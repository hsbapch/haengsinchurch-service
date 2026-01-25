package com.haengsin.church.domain.authentication.entity

import com.haengsin.church.common.BaseEntity
import jakarta.persistence.Entity


@Entity
class Users(
    val userId: String,
    val userPassword: String,
    id: Long = 0,
) : BaseEntity(id)