package com.kletter.dailies.data

import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter
import java.time.LocalDate

// конвертеры для представления типов Color и LocalDate в БД
class Converters {

    @TypeConverter
    fun fromLocalDate(date: LocalDate): Long {
        return date.toEpochDay()
    }

    @TypeConverter
    fun toLocalDate(epochDay: Long): LocalDate {
        return LocalDate.ofEpochDay(epochDay)
    }

    @TypeConverter
    fun fromColor(color: Color): Int {
        return color.value.toInt()
    }

    @TypeConverter
    fun toColor(value: Int): Color {
        return Color(value)
    }
}