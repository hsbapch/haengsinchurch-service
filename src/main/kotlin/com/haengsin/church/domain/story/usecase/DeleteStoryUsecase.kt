package com.haengsin.church.domain.story.usecase

import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.domain.story.service.StoryService
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