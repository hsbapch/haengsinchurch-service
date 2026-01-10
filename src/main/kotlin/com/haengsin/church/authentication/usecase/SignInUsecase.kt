package com.haengsin.church.authentication.usecase

import com.haengsin.church.authentication.component.CookieProvider
import com.haengsin.church.authentication.component.JwtTokenProvider
import com.haengsin.church.authentication.vo.SignInRequest
import com.haengsin.church.authentication.service.UserService
import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface

@Usecase
class SignInUsecase(
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider,
    private val cookieProvider: CookieProvider
) : UsecaseInterface<SignInRequest, Unit> {


    override fun execute(input: SignInRequest) {
        userService.getUser(input.username, input.password)
            .let(jwtTokenProvider::issueAccessToken)
            .let(cookieProvider::createAccessTokenCookie)
    }

}