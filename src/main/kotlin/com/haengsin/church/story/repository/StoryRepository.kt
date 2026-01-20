package com.haengsin.church.story.repository

import com.haengsin.church.story.entity.Story
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoryRepository: JpaRepository<Story, Long> {
}