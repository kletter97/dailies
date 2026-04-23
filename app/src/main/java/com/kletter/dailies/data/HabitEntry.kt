package com.kletter.dailies.data

import java.time.LocalDate

data class HabitEntry(
    val id: Long,
    val habitId: Long,
    val date: LocalDate,
    var completedCount: Int
)