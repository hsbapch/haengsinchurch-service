package com.haengsin.church.authentication.api

import com.haengsin.church.authentication.dto.SignInRequest
import com.haengsin.church.authentication.usecase.SignInUsecase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController(
    private val signInUsecase: SignInUsecase
) {

    @PostMapping("/login")
    fun getOAuthToken(@RequestBody request: SignInRequest)=
        signInUsecase.execute(request)

}