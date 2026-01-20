package com.haengsin.church.authentication.api

import com.haengsin.church.common.component.CookieProvider
import com.haengsin.church.authentication.dto.Token
import com.haengsin.church.authentication.vo.SignInRequest
import com.haengsin.church.authentication.usecase.SignInUsecase
import com.haengsin.church.authentication.vo.AuthenticationResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Authentication", description = "인증")
@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(
    private val signInUsecase: SignInUsecase,
    private val cookieProvider: CookieProvider,
) {

    @PostMapping("/sign-in")
    fun signIn(
        @RequestBody request: SignInRequest,
        response: HttpServletResponse
    ): AuthenticationResponse = signInUsecase.execute(request)
        .also { addTokenCookieToHeader(response, it) }
        .run { AuthenticationResponse(accessTokenExpiresIn, expiredAt) }

    @PostMapping("/sign-out")
    fun signOut(response: HttpServletResponse) = cookieProvider.deleteTokenCookie(response)


    private fun addTokenCookieToHeader(response: HttpServletResponse, token: Token) {
        cookieProvider.createTokenToCookie(token)
            .let { cookieProvider.addCookieToHeader(token, response) }
    }


}