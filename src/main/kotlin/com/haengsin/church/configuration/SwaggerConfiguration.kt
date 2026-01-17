package com.haengsin.church.configuration

import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {

    @Bean
    fun baseApi(): GroupedOpenApi = GroupedOpenApi.builder()
        .group("ALL")
        .pathsToMatch(
            "/static/**",
            "/api/**",
        )
        .addOpenApiCustomizer { openApi ->
            openApi.info(baseApiInfo())
        }
        .build()

    private fun baseApiInfo(): Info {
        return Info()
            .title("Haengsin Church Service API Docs")
            .description("Haengsin Church Service API Docs")
            .version("1.0.0")
    }

}