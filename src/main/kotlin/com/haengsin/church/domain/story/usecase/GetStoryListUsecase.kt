package com.haengsin.church.domain.story.usecase

import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.common.dto.PageResponse
import com.haengsin.church.domain.board.entity.Board
import com.haengsin.church.domain.story.entity.Story
import com.haengsin.church.domain.story.mapper.StoryMapper
import com.haengsin.church.domain.story.service.StoryService
import com.haengsin.church.domain.story.vo.GetStoryListRequest
import com.haengsin.church.domain.story.vo.StoryResponse
import org.springframework.transaction.annotation.Transactional

@Usecase
class GetStoryListUsecase(
    private val storyService: StoryService
) : UsecaseInterface<GetStoryListRequest,  PageResponse<Story>> {


    @Transactional(readOnly = true)
    override fun execute(input: GetStoryListRequest):  PageResponse<Story> =
        storyService.getStoryList(input)
            .let(PageResponse.Companion::from)

}