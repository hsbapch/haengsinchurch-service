package com.haengsin.church.domain.authentication.entity

import com.haengsin.church.common.BaseEntity
import jakarta.persistence.Entity
import org.hibernate.annotations.SQLRestriction


@SQLRestriction("is_deleted = false")
@Entity
class Users(
    val userId: String,
    val userPassword: String,
    id: Long = 0,
) : BaseEntity(id)