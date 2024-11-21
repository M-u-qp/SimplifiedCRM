package com.example.simplifiedcrm.ui.screens.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.common.extension.getColors
import com.example.simplifiedcrm.common.extension.getFormattedDay
import com.example.simplifiedcrm.common.extension.getFormattedMonth
import com.example.simplifiedcrm.data.local.database.entity.Task

@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    shape: Shape,
    task: Task,
    onDelete: (Task) -> Unit,
    onFinish: (Task) -> Unit
) {
    val activeColors = Triple(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.onSurface
    )
    val expiredColors = Triple(
        MaterialTheme.colorScheme.error,
        MaterialTheme.colorScheme.surface,
        MaterialTheme.colorScheme.surface
    )
    val doneColors = Triple(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.surface,
        MaterialTheme.colorScheme.surface
    )
    val (backgroundColor, textColor, borderColor) = task.statusTask.getColors(
        activeColors,
        expiredColors,
        doneColors
    )

    var isVisibleDeleteDialog by remember { mutableStateOf(false) }
    var responseDeleteDialog by remember { mutableStateOf(false) }
    var isVisibleFinishDialog by remember { mutableStateOf(false) }
    var responseFinishDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = responseDeleteDialog) {
        if (responseDeleteDialog) {
            onDelete(task)
            responseDeleteDialog = false
        }
    }
    LaunchedEffect(key1 = responseFinishDialog) {
        if (responseFinishDialog) {
            onFinish(task)
            responseFinishDialog = false
        }
    }
    ElevatedCard(
        modifier = modifier,
//            .border(
//                width = 1.dp,
//                color = borderColor,
//                shape = shape
//            ),
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
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
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .offset(y = (-2).dp),
                        text = task.timestamp.getFormattedMonth(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal
                        ),
                        color = textColor
                    )
                }
                if (task.statusTask == TaskByStatusSortOrder.ACTIVE.name) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { isVisibleDeleteDialog = !isVisibleDeleteDialog }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                bitmap = ImageBitmap.imageResource(R.drawable.icons8_delete),
                                contentDescription = null,
                                tint = borderColor
                            )
                        }
                        IconButton(onClick = { isVisibleFinishDialog = !isVisibleFinishDialog }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                bitmap = ImageBitmap.imageResource(R.drawable.icons8_moving),
                                contentDescription = null,
                                tint = borderColor
                            )
                        }
                    }
                } else {
                    IconButton(onClick = { isVisibleDeleteDialog = !isVisibleDeleteDialog }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            bitmap = ImageBitmap.imageResource(R.drawable.icons8_delete),
                            contentDescription = null,
                            tint = borderColor
                        )
                    }
                }
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

            if(isVisibleDeleteDialog) {
                QuestionDialog(
                    isVisibleDialog = { isVisibleDeleteDialog = it },
                    onClick = { responseDeleteDialog = it },
                    questionText = stringResource(id = R.string.delete_task)
                )
            }
            if(isVisibleFinishDialog) {
                QuestionDialog(
                    isVisibleDialog = { isVisibleFinishDialog = it },
                    onClick = { responseFinishDialog = it },
                    questionText = stringResource(id = R.string.finish_task)
                )
            }
        }
    }
}