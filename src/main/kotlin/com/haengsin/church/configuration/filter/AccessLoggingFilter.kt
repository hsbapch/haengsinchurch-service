package com.haengsin.church.configuration.filter

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component


import org.slf4j.MDC
import java.util.*
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory

@Component
@Order(1)
class AccessLoggingFilter : Filter {

    val log = LoggerFactory.getLogger(AccessLoggingFilter::class.java)

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpServletRequest = request as HttpServletRequest

        val traceId = getTraceId(httpServletRequest)
        MDC.put(TRACE_ID, traceId)

        val body = getBody(httpServletRequest)
        val fullURL = getFullUrl(httpServletRequest)

        log.info("Request ====> traceId: $traceId --- method : ${httpServletRequest.method} --- contentType : ${httpServletRequest.contentType} --- path : $fullURL --- body : $body")


        chain.doFilter(request, response)
        MDC.clear()
    }


    private fun getFullUrl(httpServletRequest: HttpServletRequest): String {
        val queryString = httpServletRequest.queryString
        return if (queryString != null) {
            "${httpServletRequest.requestURI}?$queryString"
        } else {
            httpServletRequest.requestURI
        }
    }

    private fun getBody(request: HttpServletRequest): String {
        val body = request.reader.use { it.readText() }
        val objectMapper = ObjectMapper()
        val jsonNode = try {
            objectMapper.readTree(body)
        } catch (e: Exception) {
            return body
        }
        return objectMapper.writeValueAsString(jsonNode)
    }

    private fun getTraceId(httpServletRequest: HttpServletRequest): String =
        httpServletRequest.getHeader(TRACE_ID) ?: "${INTERNAL_PREFIX}${UUID.randomUUID()}"


    companion object {
        private const val TRACE_ID = "traceId"
        private const val INTERNAL_PREFIX = "internal-"

        private const val ACTUATOR = "/actuator"
        private const val EMPTY = ""
    }
}