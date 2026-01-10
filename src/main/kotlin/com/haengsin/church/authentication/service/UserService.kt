package com.haengsin.church.authentication.service

import com.haengsin.church.authentication.dto.SignInRequest
import com.haengsin.church.authentication.entity.Users


interface UserService {

    fun getUser(userId:String, userPassword:String): Users
}