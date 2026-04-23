package com.kletter.dailies.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kletter.dailies.repositories.HabitRepository

class HabitViewModelFactory(private val repository: HabitRepository, private val habitId: Long) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        return HabitViewModel(repository, habitId) as T
    }
}