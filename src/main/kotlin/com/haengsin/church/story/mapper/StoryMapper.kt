package com.haengsin.church.story.mapper

import com.haengsin.church.story.entity.Persona
import com.haengsin.church.story.entity.Story
import com.haengsin.church.story.vo.PersonaResponse
import com.haengsin.church.story.vo.StoryResponse

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
        title = story.title,
        content = story.content,
        personaImageUrl = story.persona.imageUrl

    )
}