package com.haengsin.church.common

fun interface UsecaseInterface<I, O> {

    fun execute(input: I): O

}