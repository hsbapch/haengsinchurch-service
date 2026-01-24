package com.haengsin.church.util

import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

object OffsetDateUtils {

    private val seoulZone: ZoneId = ZoneId.of("Asia/Seoul")
    fun now(): OffsetDateTime =
        ZonedDateTime.now(seoulZone).toOffsetDateTime()

    fun toSeoul(odt: OffsetDateTime): OffsetDateTime =
        odt.withOffsetSameInstant(seoulZone.rules.getOffset(odt.toInstant()))

}