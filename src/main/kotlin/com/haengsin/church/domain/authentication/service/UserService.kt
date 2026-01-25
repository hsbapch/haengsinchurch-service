package com.haengsin.church.domain.authentication.service

import com.haengsin.church.domain.authentication.entity.Users


interface UserService {

    fun getUser(userId:String, userPassword:String): Users
}