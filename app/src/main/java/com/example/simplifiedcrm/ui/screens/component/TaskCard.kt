package com.example.simplifiedcrm.ui.screens.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.simplifiedcrm.data.local.database.entity.Task

@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    task: Task
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = task.timestamp.toString(),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = task.productName,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = task.productPrice.toString(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}