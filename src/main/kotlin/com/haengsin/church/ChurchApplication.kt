package com.haengsin.church

import com.haengsin.church.configuration.AWSProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
class ChurchApplication

fun main(args: Array<String>) {
    runApplication<ChurchApplication>(*args)
}
