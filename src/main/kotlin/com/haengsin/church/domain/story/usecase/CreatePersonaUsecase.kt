package com.haengsin.church.domain.story.usecase

import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.domain.story.service.PersonaService
import com.haengsin.church.domain.story.vo.CreatePersonaRequest
import org.springframework.transaction.annotation.Transactional

@Usecase
class CreatePersonaUsecase(
    private val personaService: PersonaService
) : UsecaseInterface<CreatePersonaRequest, Unit> {

    @Transactional
    override fun execute(input: CreatePersonaRequest): Unit {
        personaService.create(
            title = input.title,
            imageUrl = input.imageUrl,
        )
    }

}