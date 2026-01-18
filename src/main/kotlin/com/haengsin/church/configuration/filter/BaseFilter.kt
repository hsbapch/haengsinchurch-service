package com.haengsin.church.configuration.filter


import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.nio.charset.Charset
import java.util.UUID

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class BaseFilter : Filter {

    val log = LoggerFactory.getLogger(BaseFilter::class.java)

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val req = request as HttpServletRequest
        val res = response as HttpServletResponse

        // ✅ multipart는 래핑 금지(업로드 깨짐 방지)
        if (req.contentType?.startsWith("multipart/") == true) {
            chain.doFilter(req, res)
            return
        }

        val traceId = req.getHeader(TRACE_ID) ?: "$INTERNAL_PREFIX${UUID.randomUUID()}"
        MDC.put(TRACE_ID, traceId)

        val wrappedReq = ContentCachingRequestWrapper(req, REQUEST_CACHE_LIMIT)
        val wrappedRes = ContentCachingResponseWrapper(res)

        try {
            chain.doFilter(wrappedReq, wrappedRes)
        } finally {
            val requestBody = readRequestBody(wrappedReq)
            val responseBody = readResponseBody(wrappedRes)

            // ✅ 예외 핸들러에서 꺼내 쓰게 request attribute로 저장
            req.setAttribute(ATTR_CACHED_REQUEST_BODY, requestBody)
            req.setAttribute(ATTR_TRACE_ID, traceId)

            log.info("traceId=$traceId method=${req.method} path=${req.requestURI} request body=$requestBody response body=$responseBody")

            wrappedRes.copyBodyToResponse()
            MDC.clear()
        }
    }

    private fun readRequestBody(req: ContentCachingRequestWrapper): String {
        val bytes = req.contentAsByteArray
        if (bytes.isEmpty()) return ""
        val charset = req.characterEncoding?.let { Charset.forName(it) } ?: Charsets.UTF_8
        return String(bytes, charset)
    }

    private fun readResponseBody(res: ContentCachingResponseWrapper): String {
        val bytes = res.contentAsByteArray
        if (bytes.isEmpty()) return ""

        // 1) 압축된 응답이면 디코딩하지 않음 (깨짐 방지)
        val contentEncoding = res.getHeader("Content-Encoding")?.lowercase()
        if (contentEncoding == "gzip" || contentEncoding == "br" || contentEncoding == "deflate") {
            return "(encoded:$contentEncoding, ${bytes.size} bytes)"
        }

        // 2) 텍스트 계열 Content-Type만 문자열로 변환
        val contentType = res.contentType?.lowercase() ?: ""
        val isText =
            contentType.startsWith("text/") ||
                    contentType.contains("application/json") ||
                    contentType.contains("application/xml") ||
                    contentType.contains("application/javascript") ||
                    contentType.contains("application/problem+json") ||
                    contentType.contains("application/x-www-form-urlencoded")

        if (!isText) {
            return "(binary:$contentType, ${bytes.size} bytes)"
        }

        // 3) charset 결정: response.characterEncoding 우선, 없으면 Content-Type에서 추출, 그래도 없으면 UTF-8
        val charsetName = res.characterEncoding
            ?.takeIf { it.isNotBlank() }
            ?: extractCharset(contentType)
            ?: "UTF-8"

        val charset = runCatching { Charset.forName(charsetName) }.getOrElse { Charsets.UTF_8 }

        // 4) 로그 폭주 방지(필요시 조정)
        val max = 64 * 1024
        val clipped = if (bytes.size > max) bytes.copyOf(max) else bytes

        return String(clipped, charset)
    }

    private fun extractCharset(contentTypeLower: String): String? {
        // 예: "application/json; charset=utf-8"
        val idx = contentTypeLower.indexOf("charset=")
        if (idx < 0) return null
        return contentTypeLower.substring(idx + "charset=".length).trim().trimEnd(';')
            .takeIf { it.isNotBlank() }
    }


    companion object {
        private const val TRACE_ID = "traceId"
        private const val INTERNAL_PREFIX = "internal-"
        private const val REQUEST_CACHE_LIMIT = 1024 * 1024 // 1MB

        // ✅ 추가: attribute 키
        const val ATTR_CACHED_REQUEST_BODY = "cachedRequestBody"
        const val ATTR_TRACE_ID = "traceIdAttr"
    }
}
