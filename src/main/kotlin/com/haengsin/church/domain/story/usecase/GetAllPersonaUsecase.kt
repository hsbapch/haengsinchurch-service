package com.haengsin.church.domain.story.usecase

import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.domain.story.mapper.StoryMapper
import com.haengsin.church.domain.story.service.PersonaService
import com.haengsin.church.domain.story.vo.PersonaResponse

@Usecase
class GetAllPersonaUsecase(
    private val personaService: PersonaService
) : UsecaseInterface<Unit, List<PersonaResponse>> {


    override fun execute(input: Unit): List<PersonaResponse> =
        personaService.getAllPersonas()
            .map(StoryMapper::toPersonaResponse)


}