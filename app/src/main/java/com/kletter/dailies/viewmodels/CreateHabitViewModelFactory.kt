package com.kletter.dailies.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kletter.dailies.repositories.HabitRepository

class CreateHabitViewModelFactory(private val repository: HabitRepository) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        return CreateHabitViewModel(repository) as T
    }
}