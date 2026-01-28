package com.haengsin.church.domain.story.mapper

import com.haengsin.church.domain.story.entity.Persona
import com.haengsin.church.domain.story.entity.Story
import com.haengsin.church.domain.story.vo.PersonaResponse
import com.haengsin.church.domain.story.vo.StoryResponse

object StoryMapper {

    fun toPersonaResponse(
        persona: Persona
    ):PersonaResponse = PersonaResponse(
        id = persona.id,
        title = persona.title,
        imageUrl = persona.imageUrl,
    )

    fun toStoryResponse(
        story: Story
    ): StoryResponse = StoryResponse(
        id = story.id,
        title = story.title,
        content = story.content,
        personaImageUrl = story.persona.imageUrl

    )
}