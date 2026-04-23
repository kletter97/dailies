package com.kletter.dailies.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits")
    suspend fun getAllHabitsAsList(): List<HabitEntity>

    @Query("SELECT * FROM habits WHERE id = :id LIMIT 1")
    fun getHabitById(id:Long): Flow<HabitEntity?>

    @Insert
    suspend fun insert(habit: HabitEntity)

    //@Update
    //suspend fun update(habit: HabitEntity)


    @Query("""
        UPDATE habits
        SET title = :newTitle,
            dailyTarget = :newTarget
        WHERE id = :habitId
    """)
    suspend fun update(habitId: Long, newTitle: String, newTarget: Int)

    @Delete
    suspend fun delete(habit: HabitEntity)

    @Query("DELETE FROM habits WHERE id = :id")
    suspend fun deleteById(id: Long)
}