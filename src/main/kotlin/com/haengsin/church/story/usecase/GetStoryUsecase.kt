package com.haengsin.church.story.usecase

import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.story.mapper.StoryMapper
import com.haengsin.church.story.service.PersonaService
import com.haengsin.church.story.service.StoryService
import com.haengsin.church.story.vo.CreatePersonaRequest
import com.haengsin.church.story.vo.CreateStoryRequest
import com.haengsin.church.story.vo.PersonaResponse
import com.haengsin.church.story.vo.StoryResponse
import org.springframework.transaction.annotation.Transactional

@Usecase
class GetStoryUsecase(
    private val storyService: StoryService
) : UsecaseInterface<Long, StoryResponse> {


    @Transactional(readOnly = true)
    override fun execute(input: Long): StoryResponse =
        storyService.getStory(input)
            .let(StoryMapper::toStoryResponse)


}