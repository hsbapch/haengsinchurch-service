package com.haengsin.church.configuration.feign

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration


@EnableFeignClients(basePackages = ["com.haengsin.church"])
@Configuration
class FeignConfiguration