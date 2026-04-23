package com.kletter.dailies.data

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val color: Color,
    val dailyTarget: Int
)