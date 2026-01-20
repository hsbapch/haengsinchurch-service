package com.haengsin.church.story.repository

import com.haengsin.church.story.entity.Persona
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonaRepository: JpaRepository<Persona, Long> {
}