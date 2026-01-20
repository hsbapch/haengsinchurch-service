package com.haengsin.church.story.usecase

import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.story.service.PersonaService
import com.haengsin.church.story.service.StoryService
import com.haengsin.church.story.vo.UpdatePersonaRequest
import com.haengsin.church.story.vo.UpdateStoryRequest
import org.springframework.transaction.annotation.Transactional

@Usecase
class UpdateStoryUsecase(
    private val storyService: StoryService,
) : UsecaseInterface<UpdateStoryUsecase.Input, Unit> {

    data class Input(
        val storyId: Long,
        val request: UpdateStoryRequest
    )


    @Transactional
    override fun execute(input: Input) {
        storyService.getStory(input.storyId)
            .also { storyService.update(it, input.request) }

    }

}