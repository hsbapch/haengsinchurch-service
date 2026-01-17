package com.haengsin.church.configuration.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.util.ContentCachingRequestWrapper
import java.nio.charset.Charset
import java.util.*

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10) // BaseFilter(HIGHEST_PRECEDENCE) 다음에 오게
class AccessLoggingFilter : Filter {

    private val log = LoggerFactory.getLogger(AccessLoggingFilter::class.java)

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val req = request as HttpServletRequest

        // multipart는 로깅 스킵
        if (req.contentType?.startsWith("multipart/") == true) {
            chain.doFilter(request, response)
            return
        }

        // traceId는 BaseFilter에서 세팅했으면 그대로 쓰고, 없으면 여기서 생성
        val traceId = req.getHeader(TRACE_ID) ?: MDC.get(TRACE_ID) ?: "$INTERNAL_PREFIX${UUID.randomUUID()}"
        MDC.put(TRACE_ID, traceId)

        try {
            chain.doFilter(request, response)
        } finally {
            val fullURL = getFullUrl(req)
            val body = readCachedBody(request)

            log.info(
                "Request ====> traceId: $traceId --- method : ${req.method} --- contentType : ${req.contentType} --- path : $fullURL --- body : $body"
            )

            MDC.clear()
        }
    }

    private fun readCachedBody(request: ServletRequest): String {
        val wrapper = request as? ContentCachingRequestWrapper ?: return "" // BaseFilter가 래핑 못한 케이스
        val bytes = wrapper.contentAsByteArray
        if (bytes.isEmpty()) return ""
        val charset = wrapper.characterEncoding?.let { Charset.forName(it) } ?: Charsets.UTF_8
        return String(bytes, charset)
    }

    private fun getFullUrl(req: HttpServletRequest): String =
        req.queryString?.let { "${req.requestURI}?$it" } ?: req.requestURI

    companion object {
        private const val TRACE_ID = "traceId"
        private const val INTERNAL_PREFIX = "internal-"
    }
}
