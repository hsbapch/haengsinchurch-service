package com.haengsin.church.configuration.filter


import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
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

        val wrappedReq = ContentCachingRequestWrapper(req,REQUEST_CACHE_LIMIT)
        val wrappedRes = ContentCachingResponseWrapper(res)

        try {
            chain.doFilter(wrappedReq, wrappedRes)
        } finally {
            // ✅ 요청 바디는 chain 이후에 읽는다
            val requestBody = readRequestBody(wrappedReq)
            // (선택) 응답 바디 로깅
            val responseBody = readResponseBody(wrappedRes)

            // 필요하면 여기서 로깅
            // log.info("traceId=$traceId method=${req.method} path=${req.requestURI} reqBody=$requestBody resBody=$responseBody")

            // ✅ 이거 안 하면 클라이언트가 응답 바디를 못 받는다
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
        val charset = res.characterEncoding?.let { Charset.forName(it) } ?: Charsets.UTF_8
        return String(bytes, charset)
    }

    companion object {
        private const val TRACE_ID = "traceId"
        private const val INTERNAL_PREFIX = "internal-"
        private const val REQUEST_CACHE_LIMIT = 1024 * 1024 // 1MB
    }
}