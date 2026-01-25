package com.haengsin.church.domain.authentication.repository

import com.haengsin.church.domain.authentication.entity.Users
import org.springframework.data.jpa.repository.JpaRepository

interface UsersRepository : JpaRepository<Users, Long> {

    fun findUsersByUserIdAndUserPassword(userId: String, userPassword: String) : Users?

}