package com.haengsin.church.authentication.repository

import com.haengsin.church.authentication.entity.Users
import org.springframework.data.jpa.repository.JpaRepository

interface UsersRepository : JpaRepository<Users, Long> {

    fun findUsersByUserIdAndUserPassword(userId: String, userPassword: String) : Users?

}