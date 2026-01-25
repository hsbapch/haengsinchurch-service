package com.haengsin.church.domain.authentication.service


import com.haengsin.church.domain.authentication.entity.Users
import com.haengsin.church.domain.authentication.expection.UserNotFoundException
import com.haengsin.church.domain.authentication.repository.UsersRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val usersRepository: UsersRepository
) : UserService {

    override fun getUser(userId: String, userPassword: String): Users =
        usersRepository.findUsersByUserIdAndUserPassword(userId, userPassword)
            ?: throw UserNotFoundException(userId)



}