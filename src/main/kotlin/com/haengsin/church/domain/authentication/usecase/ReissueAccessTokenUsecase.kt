package com.haengsin.church.domain.authentication.usecase

import com.haengsin.church.common.component.JwtTokenProvider
import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.domain.authentication.dto.Token
import com.haengsin.church.domain.authentication.expection.TokenNotProvidedException
import jakarta.servlet.http.HttpServletRequest

@Usecase
class ReissueAccessTokenUsecase(
    private val jwtTokenProvider: JwtTokenProvider,
) : UsecaseInterface<HttpServletRequest, Token> {


    override fun execute(input: HttpServletRequest): Token {
        val refreshToken = input.cookies?.find { it.name == "REFRESH_TOKEN" }?.value
        if (refreshToken == null) throw TokenNotProvidedException()

        jwtTokenProvider.validateToken(refreshToken)

        return jwtTokenProvider.reissueAccessTokenByRefreshToken(refreshToken)

    }

}