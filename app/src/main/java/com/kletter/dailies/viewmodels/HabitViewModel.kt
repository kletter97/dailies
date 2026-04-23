package com.kletter.dailies.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kletter.dailies.data.Habit
import com.kletter.dailies.repositories.HabitRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HabitViewModel(private val repository: HabitRepository,  private val habitId: Long) : ViewModel()
{
    val habit: StateFlow<Habit?> =
        repository.getHabitById(habitId)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                null
            )

    fun onHabitEdited(title: String, dailyTarget: Int) {
        // чтобы вызывать suspend из не-suspend (для composable)
        viewModelScope.launch {
            repository.onHabitEdited(habitId, title, dailyTarget)
        }
    }
    fun onHabitDeleted() {
        // чтобы вызывать suspend из не-suspend (для composable)
        viewModelScope.launch {
            repository.onHabitDeleted(habitId)
        }
    }
}