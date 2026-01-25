package com.haengsin.church.domain.youtube.repository


import com.haengsin.church.domain.youtube.client.YoutubeClient
import com.haengsin.church.domain.youtube.dto.YoutubeLiveVideoDto
import com.haengsin.church.domain.youtube.dto.YoutubePlayListDto
import com.haengsin.church.domain.youtube.mapper.YoutubeMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class YoutubeRepository(
    private val youtubeClient: YoutubeClient,

    @Value("\${youtube.channel-id}")
    private val channelId: String,

    @Value("\${youtube.playlist-id}")
    private val playlistId: String,
) {

    fun getLiveVideoId(): YoutubeLiveVideoDto? =
        youtubeClient.getLiveVideo(channelId)
            .let(YoutubeMapper::toYoutubeListVideoDto)

    fun getPlaylist(): List<YoutubePlayListDto> =
        youtubeClient.getPlayListItems(playlistId)
            .let(YoutubeMapper::toYoutubePlayListDto)
}