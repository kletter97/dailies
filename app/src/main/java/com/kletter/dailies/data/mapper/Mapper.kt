package com.kletter.dailies.data.mapper

import com.kletter.dailies.data.Habit
import com.kletter.dailies.data.HabitEntity
import com.kletter.dailies.data.HabitEntry
import com.kletter.dailies.data.HabitEntryEntity
import java.time.LocalDate
import kotlin.Long

fun HabitEntity.toHabit(): Habit
{
    return Habit(
        id = id,
        title = title,
        color = color,
        dailyTarget = dailyTarget
    )
}
fun Habit.toHabitEntity(): HabitEntity
{
    return HabitEntity(
        id = id,
        title = title,
        color = color,
        dailyTarget = dailyTarget
    )
}

fun HabitEntryEntity.toHabitEntry(): HabitEntry
{
    return HabitEntry(
        id = id,
        habitId = habitId,
        date = date,
        completedCount = completedCount
    )
}
fun HabitEntry.toHabitEntryEntity(): HabitEntryEntity
{
    return HabitEntryEntity(
        id = id,
        habitId = habitId,
        date = date,
        completedCount = completedCount
    )
}