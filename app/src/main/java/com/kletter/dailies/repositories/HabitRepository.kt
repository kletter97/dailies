package com.kletter.dailies.repositories

import com.kletter.dailies.data.Habit
import com.kletter.dailies.data.HabitDao
import com.kletter.dailies.data.HabitEntry
import com.kletter.dailies.data.HabitEntryDao
import com.kletter.dailies.data.HabitWithStats
import com.kletter.dailies.data.mapper.*
import com.kletter.dailies.data.mapper.toHabit
import com.kletter.dailies.data.mapper.toHabitEntity
import kotlinx.coroutines.flow.*
import java.time.LocalDate
import kotlin.collections.map
import androidx.compose.ui.graphics.Color

class HabitRepository(private val habitDao: HabitDao, private val habitEntryDao: HabitEntryDao) {
    val habits = habitDao.getAllHabits().map { list -> list.map { it.toHabit() } }

    val entries = habitEntryDao.getAllEntries().map { list -> list.map { it.toHabitEntry()}}

    fun getHabitById(id:Long): Flow<Habit?>
    {
        return habitDao.getHabitById(id).map { it?.toHabit() }
    }

    fun getHabitsWithStats(): Flow<List<HabitWithStats>> =
        combine(habits, entries) { habitList, entryList ->
            val entriesByHabit = entryList.groupBy { it.habitId }
            val today = LocalDate.now()

            habitList.map { habit ->
                val habitEntries = entriesByHabit[habit.id] ?: emptyList()
                val todayDone = habitEntries.filter { it.date == today }.sumOf { it.completedCount }

                var streak = 0
                var dateCursor = today
                val sortedEntries = habitEntries.sortedByDescending { it.date }

                while (true) {
                    val entry = sortedEntries.find { it.date == dateCursor }
                    if (entry != null && entry.completedCount >= habit.dailyTarget) {
                        streak++
                        dateCursor = dateCursor.minusDays(1)
                    } else break
                }

                HabitWithStats(
                    id = habit.id, title = habit.title, color = habit.color,
                    dailyTarget = habit.dailyTarget, todayDone = todayDone,
                    isCompletedToday = todayDone >= habit.dailyTarget, streak = streak
                )
            }
        }

    suspend fun onHabitCreated(title: String, dailyTarget: Int)
    {
        habitDao.insert(
            habit = Habit(
                id = 0,
                title = title,
                color = Color(0xFFF8BBD0),
                dailyTarget = dailyTarget
            ).toHabitEntity())
    }

    suspend fun onHabitDeleted(id: Long)
    {
        println(id)
        habitDao.deleteById(id)
    }

    suspend fun onHabitEdited(habitId: Long, newTitle: String, newTarget: Int)
    {
        habitDao.update(habitId, newTitle, newTarget)
    }
    suspend fun onHabitDone(habitId: Long)
    {
        val today = LocalDate.now()
        val todayEpochDate = today.toEpochDay()
        val updatedRows = habitEntryDao.increment(habitId, todayEpochDate)

        if (updatedRows == 0) {
            habitEntryDao.insert(
                HabitEntry(
                    habitId = habitId,
                    date = today,
                    completedCount = 1,
                    id = 0 //auto-generate проигнорирует
                ).toHabitEntryEntity()
            )
        }
    }
}