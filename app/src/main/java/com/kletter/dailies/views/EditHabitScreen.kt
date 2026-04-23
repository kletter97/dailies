package com.kletter.dailies.views

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.kletter.dailies.viewmodels.HabitViewModel

@ExperimentalMaterial3Api
@Composable
fun EditHabitScreen(
    viewModel: HabitViewModel,
    navController: NavController,
    habitId: Long
) {
    HabitScreen(
        navController = navController,
        viewModel = viewModel,
        habitId = habitId,
        isEditing = true
    )
}