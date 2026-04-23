package com.kletter.dailies.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kletter.dailies.repositories.HabitRepository
import kotlinx.coroutines.launch

class CreateHabitViewModel(private val repository: HabitRepository) : ViewModel()
{
    fun onHabitCreated(title: String, dailyTarget: Int) {
        // чтобы вызывать suspend из не-suspend (для composable)
        viewModelScope.launch {
            repository.onHabitCreated(title, dailyTarget)
        }
    }
}