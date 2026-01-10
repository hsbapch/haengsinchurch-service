package com.haengsin.church.common.exception

open class InternalServerErrorException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}