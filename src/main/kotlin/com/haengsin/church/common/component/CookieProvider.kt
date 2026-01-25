package com.haengsin.church.common.component


import com.haengsin.church.domain.authentication.dto.Token
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class CookieProvider {

    fun createTokenToCookie(token: Token): List<ResponseCookie> =
        listOf(
            createAccessTokenCookie(token),
            createRefreshTokenCookie(token)
        )

    fun addCookieToResponse(cookies: List<ResponseCookie>, response: HttpServletResponse) {
        cookies.forEach {
            response.addHeader("Set-Cookie", it.toString())
        }

    }

    private fun createRefreshTokenCookie(token: Token): ResponseCookie =
        baseCookie(
            name = "REFRESH_TOKEN",
            value = token.refreshToken,
            maxAge = Duration.ofSeconds(token.refreshTokenExpiresIn)
        )

    private fun createAccessTokenCookie(token: Token): ResponseCookie =
        baseCookie(
            name = "ACCESS_TOKEN",
            value = token.accessToken,
            maxAge = Duration.ofSeconds(token.accessTokenExpiresIn)
        )

    private fun baseCookie(
        name: String,
        value: String,
        maxAge: Duration
    ): ResponseCookie =
        ResponseCookie.from(name, value)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .sameSite("None")
            .maxAge(maxAge)
            .build()
}