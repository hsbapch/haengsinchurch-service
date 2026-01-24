package com.haengsin.church.common

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Converter(autoApply = false)
class OffsetDateTimeAttributeConverter : AttributeConverter<OffsetDateTime, String> {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    override fun convertToDatabaseColumn(attribute: OffsetDateTime?): String? =
        attribute?.format(formatter)

    override fun convertToEntityAttribute(dbData: String?): OffsetDateTime? =
        dbData?.let { raw ->
            try {
                OffsetDateTime.parse(raw.trim(), formatter)
            } catch (e: DateTimeParseException) {
                var s = raw.trim().replaceFirst(' ', 'T')
                s = s.replace(Regex("\\s(?=[+-])"), "")
                s = s.replace(Regex("([+-]\\d{2})(\\d{2})$")) { m ->
                    "${m.groupValues[1]}:${m.groupValues[2]}"
                }
                s = s.replace(Regex("([+-]\\d{2})$")) { m ->
                    "${m.groupValues[1]}:00"
                }
                OffsetDateTime.parse(s, formatter)
            }
        }
}