package com.haengsin.church.domain.story.service


import com.haengsin.church.domain.story.entity.Persona
import com.haengsin.church.domain.story.entity.Story
import com.haengsin.church.domain.story.exception.StoryNotFoundException
import com.haengsin.church.domain.story.repository.StoryRepository
import com.haengsin.church.domain.story.repository.StoryRepositoryQuery
import com.haengsin.church.domain.story.vo.GetStoryListRequest
import com.haengsin.church.domain.story.vo.UpdateStoryRequest
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StoryServiceImpl(
    private val storyRepository: StoryRepository,
    private val storyRepositoryQuery: StoryRepositoryQuery,
) : StoryService {

    @Transactional
    override fun create(
        title: String,
        content: String,
        persona: Persona
    ): Story = storyRepository.save(
        Story(
            title = title,
            content = content,
            persona = persona
        )
    )

    @Transactional
    override fun update(
        story: Story,
        updateStoryRequest: UpdateStoryRequest
    ): Story = story
        .also { it.update(updateStoryRequest) }

    @Transactional
    override fun delete(story: Story) {
        story.delete()
    }


    override fun getStory(id: Long): Story =
        storyRepository.findByIdOrNull(id)
            ?: throw StoryNotFoundException(id)

    override fun getStoryList(request: GetStoryListRequest): Page<Story> =
        storyRepositoryQuery.findByPagination(request.toPageable())


    override fun getAllStories(): List<Story> =
        storyRepository.findAll()
            .takeIf(List<Story>::isNotEmpty)
            ?: emptyList()
}