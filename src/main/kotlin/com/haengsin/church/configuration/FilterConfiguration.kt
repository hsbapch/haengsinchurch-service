package com.haengsin.church.configuration

import com.haengsin.church.configuration.filter.AuthenticationFilter
import com.haengsin.church.configuration.filter.BaseFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterConfiguration {

    @Bean
    fun authenticationFilterRegistration(
        authenticationFilter: AuthenticationFilter
    ): FilterRegistrationBean<AuthenticationFilter> =
        FilterRegistrationBean(authenticationFilter).also {
            it.addUrlPatterns("/*")
            it.order = 2
        }

    @Bean
    fun requestWrappingFilterRegistration(
        filter: BaseFilter
    ): FilterRegistrationBean<BaseFilter> =
        FilterRegistrationBean(filter).also {
            it.addUrlPatterns("/*")
            it.order = 1
        }

}
