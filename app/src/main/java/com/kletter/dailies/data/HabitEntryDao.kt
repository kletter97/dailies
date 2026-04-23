package com.kletter.dailies.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitEntryDao {

    @Query("SELECT * FROM habit_entries")
    fun getAllEntries(): Flow<List<HabitEntryEntity>>

    @Query("SELECT * FROM habit_entries WHERE date = :targetEpochDate AND habitId = :habitId LIMIT 1")
    fun getEntryForDate(habitId: Long, targetEpochDate: Long): HabitEntryEntity?

    @Query("""
    UPDATE habit_entries 
    SET completedCount = completedCount + 1
    WHERE habitId = :habitId AND date = :date
""")
    suspend fun increment(habitId: Long, date: Long): Int

    @Insert
    suspend fun insert(entry: HabitEntryEntity)

    @Update
    suspend fun update(entry: HabitEntryEntity)
}