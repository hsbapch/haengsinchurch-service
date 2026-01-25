package com.haengsin.church.domain.story.service

import com.haengsin.church.domain.story.entity.Persona
import com.haengsin.church.domain.story.entity.Story
import com.haengsin.church.domain.story.vo.GetStoryListRequest
import com.haengsin.church.domain.story.vo.UpdateStoryRequest
import org.springframework.data.domain.Page


interface StoryService {

    fun create(title: String, content: String, persona: Persona): Story

    fun update(
        story: Story,
        updateStoryRequest: UpdateStoryRequest
    ): Story

    fun delete(story: Story)

    fun getStory(id: Long): Story

    fun getStoryList(request: GetStoryListRequest): Page<Story>

    fun getAllStories(): List<Story>

}