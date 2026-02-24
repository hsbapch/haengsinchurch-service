package com.haengsin.church.domain.story.entity

import com.haengsin.church.common.BaseEntity
import com.haengsin.church.domain.story.vo.UpdateStoryRequest
import com.haengsin.church.util.OffsetDateUtils
import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.hibernate.annotations.SQLRestriction
import java.time.OffsetDateTime

@SQLRestriction("is_deleted = false")
@Entity
class Story(
    title: String,
    content: String,
    personaImageUrl: String,
    id: Long = 0,
) : BaseEntity(id) {

    @Column(nullable = false)
    var title: String = title
        protected set

    @Column(columnDefinition = "TEXT", nullable = false)
    var content: String = content
        protected set

    @Column(nullable = false)
    var personaImageUrl: String = personaImageUrl
        protected set


    fun update(
        updateStoryRequest: UpdateStoryRequest
    ) {
        this.title = updateStoryRequest.title
        this.content = updateStoryRequest.content
        this.personaImageUrl =  updateStoryRequest.personaImageUrl
        this.updatedAt = OffsetDateUtils.toSeoul(OffsetDateTime.now())
    }
}