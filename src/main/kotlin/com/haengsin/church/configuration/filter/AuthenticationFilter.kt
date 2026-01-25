package com.haengsin.church.configuration.filter



import com.haengsin.church.domain.authentication.expection.TokenNotProvidedException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
class AuthenticationFilter : OncePerRequestFilter() {


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        if (request.method.equals("OPTIONS", true) ||
            !request.requestURI.startsWith("/admin") ||
            BYPASS_URLS.any { request.requestURI.contains(it) }
        ) {
            filterChain.doFilter(request, response)
            return
        }

        validateToken(request)
        filterChain.doFilter(request, response)
    }

    private fun validateToken(
        request: HttpServletRequest,
    ) {
        val accessToken = request.cookies?.find { it.name == "ACCESS_TOKEN" }?.value
        val refreshToken = request.cookies?.find { it.name == "REFRESH_TOKEN" }?.value

        if (accessToken == null && refreshToken == null) throw TokenNotProvidedException()


    }

    companion object {
        val BYPASS_URLS = listOf(
            "/api/v1/auth/sign-in"
        )
    }

}