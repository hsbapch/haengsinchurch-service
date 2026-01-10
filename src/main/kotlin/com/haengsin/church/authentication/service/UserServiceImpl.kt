package com.haengsin.church.authentication.service

import com.haengsin.church.authentication.entity.Users
import com.haengsin.church.authentication.expection.UserNotFoundException
import com.haengsin.church.authentication.repository.UsersRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val usersRepository: UsersRepository
) : UserService {

    override fun getUser(userId: String, userPassword: String): Users =
        usersRepository.findUsersByUserIdAndUserPassword(userId, userPassword)
            ?: throw UserNotFoundException(userId)



}