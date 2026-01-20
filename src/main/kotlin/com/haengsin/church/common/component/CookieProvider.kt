package com.haengsin.church.common.component

import com.haengsin.church.authentication.dto.Token
import com.haengsin.church.authentication.vo.AuthenticationResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class CookieProvider {

    fun createTokenToCookie(token: Token): ResponseCookie =
            createAccessTokenCookie(token)

    fun deleteTokenCookie(response: HttpServletResponse) =
        deleteAccessTokenCookie()
            .let { response.addHeader("Set-Cookie", it.toString()) }

    fun addCookieToHeader(
        token: Token,
        response: HttpServletResponse
    ) = createTokenToCookie(token)
        .let { response.addHeader("Set-Cookie", it.toString()) }

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

    private fun deleteAccessTokenCookie(): ResponseCookie =
        baseCookie(
            name = "ACCESS_TOKEN",
            value = "",
            maxAge = Duration.ZERO
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