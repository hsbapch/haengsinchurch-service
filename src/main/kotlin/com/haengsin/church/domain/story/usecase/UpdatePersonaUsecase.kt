package com.haengsin.church.domain.story.usecase

import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.domain.story.service.PersonaService
import com.haengsin.church.domain.story.vo.UpdatePersonaRequest
import org.springframework.transaction.annotation.Transactional

@Usecase
class UpdatePersonaUsecase(
    private val personaService: PersonaService,
) : UsecaseInterface<UpdatePersonaUsecase.Input, Unit> {

    data class Input(
        val id: Long,
        val request: UpdatePersonaRequest
    )

    @Transactional
    override fun execute(input: UpdatePersonaUsecase.Input) {
        personaService.modify(input.id, input.request.title, input.request.imageUrl)
    }
}