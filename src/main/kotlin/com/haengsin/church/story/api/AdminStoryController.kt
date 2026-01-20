package com.haengsin.church.story.api

import com.haengsin.church.story.usecase.CreateStoryUsecase
import com.haengsin.church.story.usecase.DeleteStoryUsecase
import com.haengsin.church.story.usecase.GetAllPersonaUsecase
import com.haengsin.church.story.usecase.UpdateStoryUsecase
import com.haengsin.church.story.vo.CreateStoryRequest
import com.haengsin.church.story.vo.UpdateStoryRequest
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Admin Story", description = "스토리(관리자)")
@RestController
@RequestMapping("/admin/api/v1/story")
class AdminStoryController(
    private val createStoryUsecase: CreateStoryUsecase,
    private val updateStoryUsecase: UpdateStoryUsecase,
    private val deleteStoryUsecase: DeleteStoryUsecase,
    private val getAllPersonaUsecase: GetAllPersonaUsecase,
) {

    @PostMapping
    fun createStory(
        @RequestBody request: CreateStoryRequest
    ) = createStoryUsecase.execute(request)

    @PutMapping("/{storyId}")
    fun updateStory(
        @PathVariable storyId: Long,
        @RequestBody request: UpdateStoryRequest
    ) = updateStoryUsecase.execute(
        UpdateStoryUsecase.Input(
            storyId = storyId,
            request = request
        )
    )

    @DeleteMapping("/{storyId}")
    fun deleteStory(
        @PathVariable storyId: Long,
    ) = deleteStoryUsecase.execute(storyId)


    @PostMapping("/persona")
    fun createPersona(
        @RequestBody request: CreateStoryRequest
    ) = createStoryUsecase.execute(request)


    @GetMapping("/persona/all")
    fun getAllPersona() = getAllPersonaUsecase.execute(Unit)

}
