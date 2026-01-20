package com.haengsin.church.story.service

import com.haengsin.church.story.entity.Persona

interface PersonaService {

    fun create(title: String, imageUrl: String): Persona

    fun getPersona(id: Long): Persona


    fun getAllPersonas(): List<Persona>
}