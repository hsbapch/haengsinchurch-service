package com.haengsin.church.domain.youtube.mapper

import com.haengsin.church.domain.youtube.dto.YoutubeLiveVideoDto
import com.haengsin.church.domain.youtube.dto.YoutubePlayListDto
import com.haengsin.church.domain.youtube.vo.MainYoutubeResponse
import com.haengsin.church.domain.youtube.vo.YoutubePlaylistItem
import com.haengsin.church.domain.youtube.vo.YoutubeResponse
import com.haengsin.church.domain.youtube.vo.YoutubeSearchItem


object YoutubeMapper {

    fun toYoutubeListVideoDto(
        response: YoutubeResponse<YoutubeSearchItem>
    ): YoutubeLiveVideoDto? = response.items.firstOrNull()?.let{
        YoutubeLiveVideoDto(
            videoId = it.id.videoId,
            isLive = it.isLive
        )
    }

    fun toYoutubePlayListDto(
        response: YoutubeResponse<YoutubePlaylistItem>
    ): List<YoutubePlayListDto> =
        response.items.map {
            YoutubePlayListDto(
                title = it.snippet.title,
                description = it.snippet.description,
                videoId = it.videoId,
                publishedAt = it.snippet.publishedAt
            )
        }

    fun toMainYoutubeResponse(
        dto :YoutubeLiveVideoDto
    ): MainYoutubeResponse = MainYoutubeResponse(
        videoId = dto.videoId ?: "",
        isLive = dto.isLive ?: false
    )

    fun toMainYoutubeResponse(
        dto: YoutubePlayListDto
    ): MainYoutubeResponse = MainYoutubeResponse(
        videoId = dto.videoId,
        isLive = false
    )
}