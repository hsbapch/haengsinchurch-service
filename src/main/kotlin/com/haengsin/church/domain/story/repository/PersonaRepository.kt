package com.haengsin.church.domain.story.repository


import com.haengsin.church.domain.story.entity.Persona
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonaRepository: JpaRepository<Persona, Long> {
}