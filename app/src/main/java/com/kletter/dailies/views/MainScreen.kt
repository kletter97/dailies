package com.kletter.dailies.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kletter.dailies.data.HabitWithStats
import com.kletter.dailies.viewmodels.HabitsListViewModel

@ExperimentalMaterial3Api
@Composable
fun MainScreen(
    viewModel: HabitsListViewModel,
    navController: NavController
) {
    Scaffold(
        modifier = Modifier.padding(12.dp),
        topBar = {
            TopAppBar(
                title = { Text("Dailies") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        bottomBar = {
            Button(
                onClick = { navController.navigate("create") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Create,
                        contentDescription = "Add Icon"
                    )
                    Text("Создать привычку")
                }
            }
        }
    ) { padding ->

        HabitList(
            viewModel = viewModel,
            modifier = Modifier.padding(padding),
            navController = navController
        )
    }
}

@Composable
fun HabitList(viewModel: HabitsListViewModel, modifier: Modifier, navController: NavController) {
    val habits = viewModel.habits.collectAsState()
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = modifier) {
        items(habits.value) { habit ->
            HabitItem(viewModel, habit, navController)
        }
    }
}

@Composable
fun HabitItem(viewModel: HabitsListViewModel, habit: HabitWithStats, navController: NavController)
{
    Row(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .padding(8.dp)
        .clickable {
            navController.navigate("edit/${habit.id}")
            println(habit.id)
        },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically)
    {
        Column()
        {
            Text(habit.title)
            Text("Streak ${habit.streak} days")
        }
        Button(
            onClick = {viewModel.onHabitDone(habit.id)},
            enabled = !habit.isCompletedToday){
            Text("${habit.todayDone}/${habit.dailyTarget}")
        }
    }
}