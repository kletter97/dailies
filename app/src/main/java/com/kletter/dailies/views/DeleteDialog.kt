package com.kletter.dailies.views

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Удалить привычку?") },
        text = { Text("Это действие невозможно отменить") },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Удалить")
            }
        }
    )
    println("qweqweqwe")
}