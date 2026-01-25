package com.haengsin.church.domain.story.repository


import com.haengsin.church.domain.story.entity.Story
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoryRepository: JpaRepository<Story, Long> {
}