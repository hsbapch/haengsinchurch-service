package com.haengsin.church.authentication.usecase

import com.haengsin.church.common.component.JwtTokenProvider
import com.haengsin.church.authentication.dto.Token
import com.haengsin.church.authentication.expection.TokenNotProvidedException
import com.haengsin.church.authentication.service.UserService
import com.haengsin.church.authentication.vo.SignInRequest
import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.common.component.CookieProvider
import jakarta.servlet.http.HttpServletRequest

@Usecase
class DeleteTokenUsecase(
    private val jwtTokenProvider: JwtTokenProvider
) : UsecaseInterface<Unit, Token> {


    override fun execute(input: Unit): Token =
        jwtTokenProvider.issueDeleteToken()


}