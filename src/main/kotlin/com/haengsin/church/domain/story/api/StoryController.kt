package com.haengsin.church.domain.story.api


import com.haengsin.church.domain.story.usecase.GetAllStoriesUsecase
import com.haengsin.church.domain.story.usecase.GetStoryUsecase
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Story", description = "스토리")
@RestController
@RequestMapping("/api/v1/story")
class StoryController(
    private val getAllStoriesUsecase: GetAllStoriesUsecase,
    private val getStoryUsecase: GetStoryUsecase
) {

    @GetMapping("/all")
    fun getAllStories() = getAllStoriesUsecase.execute(Unit)


    @GetMapping("/{storyId}")
    fun getStory(
        @PathVariable storyId: Long,
        ) = getStoryUsecase.execute(storyId)
}
