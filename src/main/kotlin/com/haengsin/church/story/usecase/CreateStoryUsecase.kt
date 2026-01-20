package com.haengsin.church.story.usecase

import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.story.service.PersonaService
import com.haengsin.church.story.service.StoryService
import com.haengsin.church.story.vo.CreateStoryRequest
import org.springframework.transaction.annotation.Transactional

@Usecase
class CreateStoryUsecase(
    private val storyService: StoryService,
    private val personaService: PersonaService
) : UsecaseInterface<CreateStoryRequest, Unit> {


    @Transactional
    override fun execute(input: CreateStoryRequest): Unit =
        personaService.getPersona(input.personaId)
            .let {
                storyService.create(
                    title = input.title,
                    content = input.content,
                    persona = it
                )
            }

}