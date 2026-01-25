package com.haengsin.church.youtube.usecase

import com.haengsin.church.common.Usecase
import com.haengsin.church.common.UsecaseInterface
import com.haengsin.church.youtube.mapper.YoutubeMapper
import com.haengsin.church.youtube.repository.YoutubeRepository
import com.haengsin.church.youtube.vo.MainYoutubeResponse

@Usecase
class GetMainPageYoutubeUsecase(
    private val youtubeRepository: YoutubeRepository
) : UsecaseInterface<Unit, MainYoutubeResponse> {


    override fun execute(input: Unit): MainYoutubeResponse =
        getLiveVideo() ?:getLatestPlaylistVideo()


    private fun getLiveVideo(): MainYoutubeResponse? = youtubeRepository.getLiveVideoId()
        ?.let(YoutubeMapper::toMainYoutubeResponse)

    private fun getLatestPlaylistVideo(): MainYoutubeResponse =
        youtubeRepository.getPlaylist()
            .sortedByDescending { it.publishedAt }
            .first()
            .let(YoutubeMapper::toMainYoutubeResponse)
}