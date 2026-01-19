package com.haengsin.church.common.component

import com.haengsin.church.authentication.dto.Token
import com.haengsin.church.authentication.vo.AuthenticationResponse
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

    fun addCookieToHeader(
        token: Token,
        response: HttpServletResponse
    ) = createTokenToCookie(token)
        .forEach { response.addHeader("Set-Cookie", it.toString()) }

    private fun createRefreshTokenCookie(token: Token): ResponseCookie =
        ResponseCookie.from("REFRESH_TOKEN", token.refreshToken)
            .httpOnly(true)
            .path("/")
            .sameSite("None")
            .secure(true)
            .maxAge(Duration.ofSeconds(token.refreshTokenExpiresIn))
            .build()

    private fun createAccessTokenCookie(token: Token): ResponseCookie =
        ResponseCookie.from("ACCESS_TOKEN", token.accessToken)
            .httpOnly(true)
            .sameSite("None")
            .secure(true)
            .path("/")
            .maxAge(Duration.ofSeconds(token.accessTokenExpiresIn))
            .build()

}