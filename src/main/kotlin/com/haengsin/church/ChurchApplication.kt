package com.haengsin.church

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChurchApplication

fun main(args: Array<String>) {
    runApplication<ChurchApplication>(*args)
}
