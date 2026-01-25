package com.haengsin.church.youtube.mapper

import com.haengsin.church.youtube.dto.YoutubeLiveVideoDto
import com.haengsin.church.youtube.dto.YoutubePlayListDto
import com.haengsin.church.youtube.vo.MainYoutubeResponse
import com.haengsin.church.youtube.vo.YoutubePlaylistItem
import com.haengsin.church.youtube.vo.YoutubeResponse
import com.haengsin.church.youtube.vo.YoutubeSearchItem

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