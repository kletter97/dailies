package com.kletter.dailies.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kletter.dailies.repositories.HabitRepository

class HabitsListViewModelFactory(private val repository: HabitRepository) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        return HabitsListViewModel(repository) as T
    }
}