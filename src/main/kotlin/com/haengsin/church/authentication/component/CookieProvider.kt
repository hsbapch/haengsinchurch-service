package com.haengsin.church.authentication.component

import com.haengsin.church.authentication.dto.AccessToken
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class CookieProvider {

    fun createAccessTokenCookie(accessToken: AccessToken): ResponseCookie =
        ResponseCookie.from("ACCESS_TOKEN", accessToken.accessToken)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .sameSite("Lax")
            .maxAge(Duration.ofSeconds(accessToken.expiresIn))
            .build()

}