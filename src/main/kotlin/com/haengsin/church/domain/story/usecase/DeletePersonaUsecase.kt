package com.haengsin.church.domain.story.usecase

import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.domain.story.service.PersonaService
import com.haengsin.church.domain.story.vo.UpdatePersonaRequest
import org.springframework.transaction.annotation.Transactional

@Usecase
class DeletePersonaUsecase(
    private val personaService: PersonaService,
) : UsecaseInterface<Long, Unit> {

    @Transactional
    override fun execute(input: Long) {
        personaService.delete(input)
    }
}