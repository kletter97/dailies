package com.kletter.dailies.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kletter.dailies.viewmodels.HabitViewModel

@ExperimentalMaterial3Api
@Composable
fun HabitScreen(
    navController: NavController,
    viewModel: HabitViewModel,
    habitId: Long? = null,
    isEditing: Boolean = false
) {
    var isLoaded = false
    val habit = viewModel.habit.collectAsState()
    val data = habit.value

    if (data == null && !isLoaded) {
        CircularProgressIndicator()
        isLoaded = true
        return
    }

    if (data == null && isLoaded) {
        LaunchedEffect(Unit) {
            navController.popBackStack()
        }
        return
    }

    var title by remember { mutableStateOf(data?.title ?: "") }
    var target by remember { mutableStateOf(data?.dailyTarget.toString() ?: "") }
    var showingDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.padding(12.dp),
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Редактировать" else "Создать") },
                navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                },
                actions = {
                    if(isEditing) {
                        // Правая кнопка для удаления
                        IconButton(onClick = { showingDialog = true }) {
                            Icon(Icons.Outlined.Delete, contentDescription = "Delete")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isEditing) {
                    FilledTonalButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        onClick = {
                            navController.navigate("main")
                        }
                    ) {
                        Text("Отмена")
                    }
                }

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    onClick = {
                        if (isEditing) {
                            viewModel.onHabitEdited(title, target.toInt())
                        } else {
                            //viewModel.onHabitCreated(title, target.toInt())
                        }
                        navController.navigate("main")
                    }
                ) {
                    Text(if (isEditing) "Сохранить" else "Создать")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = padding)
                .padding(16.dp)
                .statusBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Название") }
            )

            OutlinedTextField(
                value = target,
                onValueChange = { target = it },
                label = { Text("Цель в день") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }

    if (showingDialog) {
        DeleteDialog(
            onDismiss = { showingDialog = false },
            onConfirm = {
                navController.navigate("main")
                showingDialog = false
                if (habitId != null) {
                    viewModel.onHabitDeleted()
                }
            }
        )
    }
}