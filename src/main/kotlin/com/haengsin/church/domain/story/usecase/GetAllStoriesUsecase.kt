package com.haengsin.church.domain.story.usecase

import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.domain.story.mapper.StoryMapper
import com.haengsin.church.domain.story.service.StoryService
import com.haengsin.church.domain.story.vo.StoryResponse
import org.springframework.transaction.annotation.Transactional

@Usecase
class GetAllStoriesUsecase(
    private val storyService: StoryService
) : UsecaseInterface<Unit, List<StoryResponse>> {


    @Transactional(readOnly = true)
    override fun execute(input: Unit): List<StoryResponse> =
        storyService.getAllStories()
            .map(StoryMapper::toStoryResponse)


}