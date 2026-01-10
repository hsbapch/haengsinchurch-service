package com.haengsin.church.authentication.usecase

import com.haengsin.church.authentication.dto.SignInRequest
import com.haengsin.church.authentication.service.UserService
import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface

@Usecase
class SignInUsecase(
    private val userService: UserService
) : UsecaseInterface<SignInRequest, Unit> {


    override fun execute(input: SignInRequest) {
        userService.getUser(input.username, input.password)
    }

}