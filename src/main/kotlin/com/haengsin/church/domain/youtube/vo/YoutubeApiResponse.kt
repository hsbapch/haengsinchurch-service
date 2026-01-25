package com.haengsin.church.domain.youtube.vo

import java.time.OffsetDateTime

/**
 * YouTube list 계열 API 공통 Response
 * - search.list
 * - playlistItems.list
 */
data class YoutubeResponse<T>(
    val kind: String,
    val etag: String,
    val regionCode: String? = null,
    val nextPageToken: String? = null,
    val items: List<T>,
    val pageInfo: YoutubePageInfo
)

/**
 * pageInfo (공통)
 */
data class YoutubePageInfo(
    val totalResults: Int,
    val resultsPerPage: Int
)

/* =========================================================
 * search.list 전용
 * ========================================================= */

data class YoutubeSearchItem(
    val kind: String,
    val etag: String,
    val id: YoutubeSearchId,
    val snippet: YoutubeSearchSnippet
) {
    val isLive: Boolean
        get() = snippet.liveBroadcastContent == "live"
}

data class YoutubeSearchId(
    val kind: String,
    val videoId: String
)

data class YoutubeSearchSnippet(
    val publishedAt: String,
    val channelId: String,
    val title: String,
    val description: String,
    val thumbnails: YoutubeThumbnails,
    val channelTitle: String,
    val liveBroadcastContent: String,
    val publishTime: String
)

/* =========================================================
 * playlistItems.list 전용
 * ========================================================= */

data class YoutubePlaylistItem(
    val kind: String,
    val etag: String,
    val id: String,
    val snippet: YoutubePlaylistSnippet,
    val contentDetails: YoutubeContentDetails
) {
    val videoId: String
        get() = contentDetails.videoId
}

data class YoutubePlaylistSnippet(
    val publishedAt: OffsetDateTime,
    val channelId: String,
    val title: String,
    val description: String,
    val thumbnails: YoutubeThumbnails,
    val channelTitle: String,
    val playlistId: String,
    val position: Int,
    val resourceId: YoutubeResourceId,
    val videoOwnerChannelTitle: String?,
    val videoOwnerChannelId: String?
)

data class YoutubeContentDetails(
    val videoId: String,
    val videoPublishedAt: String
)

data class YoutubeResourceId(
    val kind: String,
    val videoId: String
)

/* =========================================================
 * thumbnails (완전 공통)
 * ========================================================= */

data class YoutubeThumbnails(
    val default: YoutubeThumbnail,
    val medium: YoutubeThumbnail,
    val high: YoutubeThumbnail,
    val standard: YoutubeThumbnail? = null,
    val maxres: YoutubeThumbnail? = null
)

data class YoutubeThumbnail(
    val url: String,
    val width: Int,
    val height: Int
)
