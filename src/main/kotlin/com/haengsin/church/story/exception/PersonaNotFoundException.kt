package com.haengsin.church.story.exception

import com.haengsin.church.common.exception.NotFoundException

class PersonaNotFoundException(id: Long): NotFoundException(
    "Persona with id $id not found."
)