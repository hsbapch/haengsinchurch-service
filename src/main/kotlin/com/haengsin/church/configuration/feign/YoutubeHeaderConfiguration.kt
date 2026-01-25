package com.haengsin.church.configuration.feign


import feign.RequestInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean


class YoutubeHeaderConfiguration {

    @Bean
    fun requestInterceptor(@Value("\${youtube.x-goog-api-key}") key: String): RequestInterceptor {
        return RequestInterceptor {
            it.header(API_HEADER, key)
        }
    }

    companion object {
        private const val API_HEADER = "x-goog-api-key"
    }
}