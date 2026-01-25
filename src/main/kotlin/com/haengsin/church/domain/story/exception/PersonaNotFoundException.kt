package com.haengsin.church.domain.story.exception

import com.haengsin.church.common.exception.NotFoundException

class PersonaNotFoundException(id: Long): NotFoundException(
    "Persona with id $id not found."
)