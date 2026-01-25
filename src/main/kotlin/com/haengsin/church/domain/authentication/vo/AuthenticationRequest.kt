package com.haengsin.church.domain.authentication.vo

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SignInRequest (

    @field:Schema(description = "아이디")
    @field:NotBlank
    val username: String,

    @field:Schema(description = "비밀번호")
    @field:NotBlank
    val password: String

)