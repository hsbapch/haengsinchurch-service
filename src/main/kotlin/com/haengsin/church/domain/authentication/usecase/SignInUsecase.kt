package com.haengsin.church.domain.authentication.usecase

import com.haengsin.church.common.component.JwtTokenProvider
import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.domain.authentication.dto.Token
import com.haengsin.church.domain.authentication.service.UserService
import com.haengsin.church.domain.authentication.vo.SignInRequest

@Usecase
class SignInUsecase(
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider,
) : UsecaseInterface<SignInRequest, Token> {


    override fun execute(input: SignInRequest): Token =
        userService.getUser(input.username, input.password)
            .let { jwtTokenProvider.issueToken(it.id) }


}