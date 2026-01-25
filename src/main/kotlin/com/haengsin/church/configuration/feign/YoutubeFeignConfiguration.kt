package com.haengsin.church.configuration.feign

import feign.FeignException
import feign.Logger
import feign.Response
import feign.codec.ErrorDecoder
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import java.nio.charset.Charset


class YoutubeFeignConfiguration {

    @Bean
    fun feignLoggerLevel() = Logger.Level.FULL

    @Bean
    fun feignErrorDecoder() = FeignClientErrorDecoder()


}

class FeignClientErrorDecoder(
    private val errorDecoder: ErrorDecoder = ErrorDecoder.Default(),
) : ErrorDecoder {

    val log = LoggerFactory.getLogger(FeignClientErrorDecoder::class.java)

    override fun decode(methodKey: String?, response: Response?): Exception {
        log.error("{} 요청 실패, status : {}, reason : {}", methodKey, response!!.status(), response.reason())

        val httpStatus = HttpStatus.valueOf(response.status())
        return if (httpStatus.is5xxServerError) {
            FeignException.InternalServerError(
                String(response.body().asInputStream().readAllBytes(), Charset.defaultCharset()),
                response.request(),
                response.request().body(),
                response.request().headers()
            )
        } else if (httpStatus.is4xxClientError) {
            FeignException.NotFound(
                String(response.body().asInputStream().readAllBytes(), Charset.defaultCharset()),
                response.request(),
                response.request().body(),
                response.request().headers()
            )
        } else {
            errorDecoder.decode(methodKey, response)
        }
    }
}