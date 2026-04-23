package com.kletter.dailies.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kletter.dailies.data.HabitWithStats
import com.kletter.dailies.repositories.HabitRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HabitsListViewModel(private val repository: HabitRepository) : ViewModel()
{
    val habits: StateFlow<List<HabitWithStats>> =
        repository.getHabitsWithStats()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
    fun onHabitDone(habitId: Long) {
        // чтобы вызывать suspend из не-suspend (для composable)
        viewModelScope.launch {
            repository.onHabitDone(habitId)
        }
    }
}