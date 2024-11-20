package com.example.simplifiedcrm.ui.screens.task_creation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.common.extension.getFormattedDate
import com.example.simplifiedcrm.ui.screens.component.DatePickerDialog
import java.util.Date

@Composable
fun TaskCalendar(
    onSelectedDate: (Date) -> Unit,
    externalDate: Date?
) {
    var isVisibleDatePicker by remember { mutableStateOf(false) }
    val selectedDate = remember { mutableStateOf(externalDate) }

    ElevatedCard(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.primary
            ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 24.dp,
                    vertical = 10.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val dateText = if (selectedDate.value == null) {
                stringResource(id = R.string.minimum_time_active_task_24_hours)
            } else {
                selectedDate.value!!.getFormattedDate()
            }
            Text(
                modifier = Modifier.weight(1f),
                text = dateText,
                style = MaterialTheme.typography.bodyMedium
            )
            IconButton(
                modifier = Modifier.padding(start = 10.dp),
                onClick = { isVisibleDatePicker = !isVisibleDatePicker }
            ) {
                Icon(
                    bitmap = ImageBitmap.imageResource(R.drawable.icons8_calendar),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        if (isVisibleDatePicker) {
            DatePickerDialog(
                isVisibleDialog = { isVisibleDatePicker = it },
                onDateSelected = { date ->
                    selectedDate.value = date
                    onSelectedDate(date)
                }
            )
        }
    }
}