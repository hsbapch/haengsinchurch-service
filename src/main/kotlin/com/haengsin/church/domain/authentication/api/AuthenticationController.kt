package com.haengsin.church.domain.authentication.api

import com.haengsin.church.common.component.CookieProvider
import com.haengsin.church.domain.authentication.dto.Token
import com.haengsin.church.domain.authentication.usecase.DeleteTokenUsecase
import com.haengsin.church.domain.authentication.usecase.ReissueAccessTokenUsecase
import com.haengsin.church.domain.authentication.usecase.SignInUsecase
import com.haengsin.church.domain.authentication.vo.AuthenticationResponse
import com.haengsin.church.domain.authentication.vo.SignInRequest
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
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
    private val reissueAccessTokenUsecase: ReissueAccessTokenUsecase,
    private val deleteTokenUsecase: DeleteTokenUsecase,
) {

    @PostMapping("/sign-in")
    fun signIn(
        @RequestBody request: SignInRequest,
        response: HttpServletResponse
    ): AuthenticationResponse = tokenResponse(
        token = signInUsecase.execute(request),
        response = response
    )

    @PostMapping("/sign-out")
    fun signOut(
        response: HttpServletResponse
    ):AuthenticationResponse = tokenResponse(
        token = deleteTokenUsecase.execute(Unit),
        response = response
    )

    @PostMapping("/refresh-token")
    fun refreshToken(
        request: HttpServletRequest,
        response: HttpServletResponse
    ):AuthenticationResponse = tokenResponse(
        token = reissueAccessTokenUsecase.execute(request),
        response = response
    )

    private fun addTokenCookieToHeader(response: HttpServletResponse, token: Token) {
        cookieProvider.createTokenToCookie(token)
            .let { cookieProvider.addCookieToResponse(it, response) }
    }

    private fun tokenResponse(token: Token, response: HttpServletResponse): AuthenticationResponse =
        token
            .also { addTokenCookieToHeader(response, it) }
            .run { AuthenticationResponse(accessTokenExpiresIn, expiredAt) }


}