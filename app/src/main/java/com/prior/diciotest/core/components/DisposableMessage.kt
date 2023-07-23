package com.prior.diciotest.core.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DisposableMessage(
    message: String,
    onDismiss: () -> Unit,
){
    if(message == "")
        return

    AlertDialog(
        onDismissRequest = { onDismiss() },
        text = { Text(text = message) },
        confirmButton = { },
    )
}