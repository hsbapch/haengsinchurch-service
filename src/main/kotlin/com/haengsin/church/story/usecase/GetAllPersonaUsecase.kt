package com.haengsin.church.story.usecase

import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.story.mapper.StoryMapper
import com.haengsin.church.story.service.PersonaService
import com.haengsin.church.story.service.StoryService
import com.haengsin.church.story.vo.CreatePersonaRequest
import com.haengsin.church.story.vo.CreateStoryRequest
import com.haengsin.church.story.vo.PersonaResponse
import org.springframework.transaction.annotation.Transactional

@Usecase
class GetAllPersonaUsecase(
    private val personaService: PersonaService
) : UsecaseInterface<Unit, List<PersonaResponse>> {


    override fun execute(input: Unit): List<PersonaResponse> =
        personaService.getAllPersonas()
            .map(StoryMapper::toPersonaResponse)


}