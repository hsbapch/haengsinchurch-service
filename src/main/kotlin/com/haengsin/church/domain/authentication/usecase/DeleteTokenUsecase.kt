package com.haengsin.church.domain.authentication.usecase

import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.common.component.JwtTokenProvider
import com.haengsin.church.domain.authentication.dto.Token

@Usecase
class DeleteTokenUsecase(
    private val jwtTokenProvider: JwtTokenProvider
) : UsecaseInterface<Unit, Token> {


    override fun execute(input: Unit): Token =
        jwtTokenProvider.issueDeleteToken()


}