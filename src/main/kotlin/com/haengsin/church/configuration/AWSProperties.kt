package com.haengsin.church.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@ConfigurationProperties(prefix = "aws.s3")
data class AWSProperties(
    var bucket: String = "",
    var region: String = "",
    var accessKey: String = "",
    var secretKey: String = ""
)