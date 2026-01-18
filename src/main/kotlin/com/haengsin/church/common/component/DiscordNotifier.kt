package com.haengsin.church.common.component

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class DiscordNotifier(
    private val restTemplate: RestTemplate = RestTemplate(),
) {

    @Value("\${discord.error-webhook-url}")
    private lateinit var webhookUrl: String

    fun send(title: String, description: String) {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }

        val payload = mapOf(
            "embeds" to listOf(
                mapOf(
                    "title" to title,
                    "description" to description.take(5500)
                )
            )
        )

        val entity = HttpEntity(payload, headers)

        runCatching {
            restTemplate.postForEntity(webhookUrl, entity, String::class.java)
        }
    }
}
