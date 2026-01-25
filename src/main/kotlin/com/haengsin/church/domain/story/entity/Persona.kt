package com.haengsin.church.domain.story.entity

import com.haengsin.church.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

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

}