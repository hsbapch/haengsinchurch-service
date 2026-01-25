package com.haengsin.church.domain.story.service


import com.haengsin.church.domain.story.entity.Persona
import com.haengsin.church.domain.story.exception.PersonaNotFoundException
import com.haengsin.church.domain.story.repository.PersonaRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import kotlin.collections.isNotEmpty

@Service
class PersonaServiceImpl(
    private val personaRepository: PersonaRepository
): PersonaService {

    @Transactional
    override fun create(title: String, imageUrl: String): Persona =
        personaRepository.save(
            Persona(
                title = title,
                imageUrl = imageUrl
            )
        )

    override fun getPersona(id: Long): Persona =
        personaRepository.findByIdOrNull(id)
            ?:  throw PersonaNotFoundException(id)


    override fun getAllPersonas(): List<Persona> =
        personaRepository.findAll()
            .takeIf(List<Persona>::isNotEmpty)
            ?: emptyList()

}