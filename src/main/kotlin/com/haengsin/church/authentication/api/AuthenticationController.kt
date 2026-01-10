package com.haengsin.church.authentication.api

import com.haengsin.church.authentication.vo.SignInRequest
import com.haengsin.church.authentication.usecase.SignInUsecase
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController(
    private val signInUsecase: SignInUsecase
) {

    @PostMapping("/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
        response: HttpServletResponse
    ): ResponseEntity<Void> {

        val cookie = signInUsecase.execute(request)

        response.addHeader("Set-Cookie", cookie.toString())
        return ResponseEntity.ok().build()
    }

}