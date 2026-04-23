package com.kletter.dailies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.kletter.dailies.data.AppDatabase
import com.kletter.dailies.repositories.HabitRepository
import com.kletter.dailies.ui.theme.DailiesTheme
import com.kletter.dailies.viewmodels.CreateHabitViewModel
import com.kletter.dailies.viewmodels.CreateHabitViewModelFactory
import com.kletter.dailies.viewmodels.HabitViewModel
import com.kletter.dailies.viewmodels.HabitViewModelFactory
import com.kletter.dailies.viewmodels.HabitsListViewModel
import com.kletter.dailies.viewmodels.HabitsListViewModelFactory
import com.kletter.dailies.views.CreateHabitScreen
import com.kletter.dailies.views.EditHabitScreen
import com.kletter.dailies.views.MainScreen

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_db"
        ).build()

        val repository = HabitRepository(
            db.habitDao(),
            db.habitEntryDao()
        )

        enableEdgeToEdge()
        setContent {
            DailiesTheme {
                AppNavGraph(repository)
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun AppNavGraph(repository: HabitRepository) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {

        composable("main") {
            val vm: HabitsListViewModel = viewModel(
                factory = HabitsListViewModelFactory(repository)
            )

            MainScreen(vm, navController)
        }

        composable("create") {
            val vm: CreateHabitViewModel = viewModel(
                factory = CreateHabitViewModelFactory(repository)
            )

            CreateHabitScreen(vm, navController)
        }

        composable("edit/{habitId}") {
            backStackEntry ->
                val habitId = backStackEntry.arguments?.getString("habitId")?.toLong()
            val vm: HabitViewModel = viewModel(
                factory = HabitViewModelFactory(repository, habitId!!)
            )

            EditHabitScreen(vm, navController, habitId)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DailiesTheme {
        Greeting("Android")
    }
}