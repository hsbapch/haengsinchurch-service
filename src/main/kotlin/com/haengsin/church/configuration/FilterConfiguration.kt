package com.haengsin.church.configuration

import com.haengsin.church.configuration.filter.AccessLoggingFilter
import com.haengsin.church.configuration.filter.AuthenticationFilter
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
        filter: AccessLoggingFilter
    ): FilterRegistrationBean<AccessLoggingFilter> =
        FilterRegistrationBean(filter).also {
            it.addUrlPatterns("/*")
            it.order = 1
        }

    @Bean
    fun accessLoggingFilterRegistration(
        accessLoggingFilter: AccessLoggingFilter
    ): FilterRegistrationBean<AccessLoggingFilter> =
        FilterRegistrationBean(accessLoggingFilter).also {
            it.addUrlPatterns("/*")
            it.order = 0
        }
}
