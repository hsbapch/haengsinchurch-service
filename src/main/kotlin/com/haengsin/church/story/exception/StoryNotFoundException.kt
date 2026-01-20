package com.haengsin.church.story.exception

import com.haengsin.church.common.exception.NotFoundException

class StoryNotFoundException(id: Long): NotFoundException(
    "Story with id $id not found."
)