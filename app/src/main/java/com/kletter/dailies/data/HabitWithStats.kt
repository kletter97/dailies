package com.kletter.dailies.data

import androidx.compose.ui.graphics.Color

data class HabitWithStats(
    val id: Long,
    val title: String,
    val color: Color,
    val dailyTarget: Int,

    val todayDone: Int,
    val isCompletedToday: Boolean,
    val streak: Int
) {
}