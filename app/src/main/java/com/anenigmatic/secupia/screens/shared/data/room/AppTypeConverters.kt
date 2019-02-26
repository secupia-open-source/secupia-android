package com.anenigmatic.secupia.screens.shared.data.room

import androidx.room.TypeConverter
import org.threeten.bp.LocalDateTime

class AppTypeConverters {

    @TypeConverter
    fun fromLocalDateTimeToString(ldt: LocalDateTime): String {
        return ldt.toString()
    }

    @TypeConverter
    fun fromStringToLocalDateTime(str: String): LocalDateTime {
        return LocalDateTime.parse(str)
    }
}