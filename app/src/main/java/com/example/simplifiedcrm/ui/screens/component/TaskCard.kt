package com.example.simplifiedcrm.ui.screens.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.simplifiedcrm.common.extension.getFormattedDate
import com.example.simplifiedcrm.data.local.database.entity.Task

@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    shape: Shape,
    task: Task
) {
    val backgroundColor = when (task.statusTask) {
        TaskByStatusSortOrder.ACTIVE.name -> { MaterialTheme.colorScheme.scrim }
        TaskByStatusSortOrder.EXPIRED.name -> { MaterialTheme.colorScheme.error }
        TaskByStatusSortOrder.DONE.name -> { MaterialTheme.colorScheme.primary }
        else -> { MaterialTheme.colorScheme.error }
    }
    val textColor = when (task.statusTask) {
        TaskByStatusSortOrder.ACTIVE.name -> { MaterialTheme.colorScheme.primary }
        TaskByStatusSortOrder.EXPIRED.name -> { MaterialTheme.colorScheme.onSurface }
        TaskByStatusSortOrder.DONE.name -> { MaterialTheme.colorScheme.onSurface }
        else -> { MaterialTheme.colorScheme.error }
    }

    ElevatedCard(
        modifier = modifier,
        shape = shape,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = task.timestamp.getFormattedDate(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = task.productName,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = task.productPrice.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = task.client.name,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = task.client.phone,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor
                )
            }
        }
    }
}