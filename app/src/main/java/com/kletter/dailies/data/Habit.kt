package com.kletter.dailies.data

import androidx.compose.ui.graphics.Color

data class Habit(
    val id: Long,
    val title: String,
    val color: Color,
    val dailyTarget: Int
)