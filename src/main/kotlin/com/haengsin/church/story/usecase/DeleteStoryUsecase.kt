package com.haengsin.church.story.usecase

import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.story.service.PersonaService
import com.haengsin.church.story.service.StoryService
import com.haengsin.church.story.vo.UpdatePersonaRequest
import com.haengsin.church.story.vo.UpdateStoryRequest
import org.springframework.transaction.annotation.Transactional

@Usecase
class DeleteStoryUsecase(
    private val storyService: StoryService,
) : UsecaseInterface<Long, Unit> {


    @Transactional
    override fun execute(input: Long) {
        storyService.getStory(input)
            .let(storyService::delete)

    }

}