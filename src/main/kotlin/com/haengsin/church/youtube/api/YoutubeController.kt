package com.haengsin.church.youtube.api

import com.haengsin.church.youtube.usecase.GetMainPageYoutubeUsecase
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Youtube 채널 관련 API", description = "행신침례교회 유튜브 채널 관련 API")
@RestController
@RequestMapping("/api/v1/youtube")
class YoutubeController(
    private val getMainPageYoutubeUsecase: GetMainPageYoutubeUsecase
) {


    @GetMapping("/main-page-video")
    fun getMainPageVideo() =getMainPageYoutubeUsecase.execute(Unit)


}