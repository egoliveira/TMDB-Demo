package br.com.gielamo.tmdb.data.mechanism.json

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeParseException

class LocalDateTypeAdapter {
    @ToJson
    fun toJSON(localDate: LocalDate?): String? {
        return localDate?.toString()
    }

    @FromJson
    fun fromJson(json: String): LocalDate? {
        return if (json.isNotBlank()) {
            try {
                LocalDate.parse(json)
            } catch (ex: DateTimeParseException) {
                Timber.e(ex, "Can't parse %s as LocalDate", json)

                null
            }
        } else null
    }
}