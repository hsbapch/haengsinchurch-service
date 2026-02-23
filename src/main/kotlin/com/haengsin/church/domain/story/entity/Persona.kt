package com.haengsin.church.domain.story.entity

import com.haengsin.church.common.BaseEntity
import com.haengsin.church.common.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.hibernate.annotations.SQLRestriction

@SQLRestriction("is_deleted = false")
@Entity
class Persona(
    title: String,
    imageUrl: String,
    id: Long = 0,
) : BaseEntity(id) {


    @Column(nullable = false)
    var title: String = title
        protected set

    @Column(nullable = false, columnDefinition = "TEXT")
    var imageUrl: String = imageUrl
        protected set


    fun modify(title: String, imageUrl: String) {
        this.title = title
        this.imageUrl = imageUrl
    }
}