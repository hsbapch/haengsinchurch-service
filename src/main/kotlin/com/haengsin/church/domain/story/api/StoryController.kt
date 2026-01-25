package com.haengsin.church.domain.story.api


import com.haengsin.church.domain.story.usecase.GetAllStoriesUsecase
import com.haengsin.church.domain.story.usecase.GetStoryListUsecase
import com.haengsin.church.domain.story.usecase.GetStoryUsecase
import com.haengsin.church.domain.story.vo.GetStoryListRequest
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Story", description = "스토리")
@RestController
@RequestMapping("/api/v1/story")
class StoryController(
    private val getAllStoriesUsecase: GetAllStoriesUsecase,
    private val getStoryUsecase: GetStoryUsecase,
    private val getStoryListUsecase: GetStoryListUsecase,
) {

    @GetMapping("/all")
    fun getAllStories() = getAllStoriesUsecase.execute(Unit)


    @GetMapping("/{storyId}")
    fun getStory(
        @PathVariable storyId: Long,
        ) = getStoryUsecase.execute(storyId)

    @GetMapping("/list")
    fun getStoryList(
        @ModelAttribute request: GetStoryListRequest
    ) = getStoryListUsecase.execute(request)

}
