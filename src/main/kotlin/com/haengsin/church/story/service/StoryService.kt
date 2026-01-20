package com.haengsin.church.story.service

import com.haengsin.church.story.entity.Persona
import com.haengsin.church.story.entity.Story
import com.haengsin.church.story.vo.UpdateStoryRequest

interface StoryService {

    fun create(title: String, content: String, persona: Persona): Story

    fun update(
        story: Story,
        updateStoryRequest: UpdateStoryRequest
    ): Story

    fun delete(story: Story)

    fun getStory(id: Long): Story

    fun getAllStories(): List<Story>

}