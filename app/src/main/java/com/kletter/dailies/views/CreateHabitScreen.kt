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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kletter.dailies.viewmodels.CreateHabitViewModel

@ExperimentalMaterial3Api
@Composable
fun CreateHabitScreen(
    viewModel: CreateHabitViewModel,
    navController: NavController
) {


    var title by remember { mutableStateOf("") }
    var target by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.padding(12.dp),
        topBar = {
            TopAppBar(
                title = { Text("Создать") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    onClick = {
                        viewModel.onHabitCreated(title, target.toInt())
                        navController.navigate("main")
                    }
                ) {
                    Text("Создать")
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
}