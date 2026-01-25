package com.haengsin.church.domain.story.entity

import com.haengsin.church.common.BaseEntity
import com.haengsin.church.domain.story.vo.UpdateStoryRequest
import com.haengsin.church.util.OffsetDateUtils
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToOne
import java.time.OffsetDateTime

@Entity
class Story(
    title: String,
    content: String,
    persona: Persona,
    id: Long = 0,
) : BaseEntity(id) {

    @Column(nullable = false)
    var title: String = title
        protected set

    @Column(columnDefinition = "TEXT", nullable = false)
    var content: String = content
        protected set

    @OneToOne(fetch = FetchType.LAZY)
    var persona: Persona = persona
        protected set


    fun update(
        updateStoryRequest: UpdateStoryRequest
    ) {
        this.title = updateStoryRequest.title
        this.content = updateStoryRequest.content
        this.updatedAt = OffsetDateUtils.toSeoul(OffsetDateTime.now())
    }
}