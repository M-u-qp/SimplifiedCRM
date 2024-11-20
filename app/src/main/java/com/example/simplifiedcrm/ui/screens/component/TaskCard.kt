package com.example.simplifiedcrm.ui.screens.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.simplifiedcrm.common.extension.getColors
import com.example.simplifiedcrm.common.extension.getFormattedDay
import com.example.simplifiedcrm.common.extension.getFormattedMonth
import com.example.simplifiedcrm.data.local.database.entity.Task

@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    shape: Shape,
    task: Task
) {
    val activeColors = Triple(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.tertiary
    )
    val expiredColors = Triple(
        MaterialTheme.colorScheme.error,
        MaterialTheme.colorScheme.surface,
        MaterialTheme.colorScheme.surface
    )
    val doneColors = Triple(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.onTertiary,
        MaterialTheme.colorScheme.onTertiary
    )
    val (backgroundColor, textColor, borderColor) = task.statusTask.getColors(
        activeColors,
        expiredColors,
        doneColors
    )

    ElevatedCard(
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shape
            ),
        shape = shape,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = task.timestamp.getFormattedDay(),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = textColor
                )
                Text(
                    modifier = Modifier.offset(y = (-2).dp),
                    text = task.timestamp.getFormattedMonth(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    color = textColor
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 20.dp),
                color = borderColor
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
                    overflow = TextOverflow.Ellipsis,
                    color = textColor
                )
                Text(
                    text = task.productPrice.toString(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = textColor
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 20.dp),
                color = borderColor
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = task.client.name,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor
                )
                Text(
                    text = task.client.phone,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = textColor
                )
            }
        }
    }
}