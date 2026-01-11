package com.haengsin.church.configuration.filter


import com.haengsin.church.common.component.CookieProvider
import com.haengsin.church.common.component.JwtTokenProvider
import com.haengsin.church.authentication.expection.TokenNotProvidedException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
class AuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val cookieProvider: CookieProvider
) : OncePerRequestFilter() {


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        if (
            !request.requestURI.startsWith("/admin") ||
            BYPASS_URLS.any { request.requestURI.contains(it) }
        ) {
            filterChain.doFilter(request, response)
            return
        }

        resolveToken(request, response)
        filterChain.doFilter(request, response)
    }

    private fun resolveToken(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        val accessToken = request.cookies?.find { it.name == "ACCESS_TOKEN" }?.value
        val refreshToken = request.cookies?.find { it.name == "REFRESH_TOKEN" }?.value

        if (accessToken == null || refreshToken == null) {
            throw TokenNotProvidedException()
        }

//        jwtTokenProvider.validateToken(accessToken)

        if (jwtTokenProvider.isReissueNeeded(accessToken)) {
            jwtTokenProvider.reissueAccessTokenByRefreshToken(refreshToken)
                .let { cookieProvider.addCookieToHeader(it, response) }
        } else {
            return
        }
    }

    companion object {
        val BYPASS_URLS = listOf(
            "/api/v1/auth/sign-in"
        )
    }

}