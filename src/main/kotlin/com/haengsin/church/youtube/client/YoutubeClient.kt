package com.haengsin.church.youtube.client

import com.haengsin.church.configuration.feign.YoutubeFeignConfiguration
import com.haengsin.church.configuration.feign.YoutubeHeaderConfiguration
import com.haengsin.church.youtube.vo.YoutubePlaylistItem
import com.haengsin.church.youtube.vo.YoutubeResponse
import com.haengsin.church.youtube.vo.YoutubeSearchItem
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "youtube-client",
    url = "\${youtube.url}",
    configuration = [YoutubeHeaderConfiguration::class, YoutubeFeignConfiguration::class]
)
interface YoutubeClient {

    @GetMapping("/v3/search?part=snippet&type=video&eventType=live")
    fun getLiveVideo(@RequestParam("channelId") channelId: String): YoutubeResponse<YoutubeSearchItem>

    @GetMapping("/v3/playlistItems?part=snippet,contentDetails")
    fun getPlayListItems(@RequestParam("playlistId") playlistId: String): YoutubeResponse<YoutubePlaylistItem>

}