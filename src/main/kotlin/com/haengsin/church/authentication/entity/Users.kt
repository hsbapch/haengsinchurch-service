package com.haengsin.church.authentication.entity

import com.haengsin.church.common.BaseTimeEntity
import jakarta.persistence.Entity


@Entity
class Users(
    val userId: String,
    val userPassword: String,
    id: Long = 0,
) : BaseTimeEntity(id)