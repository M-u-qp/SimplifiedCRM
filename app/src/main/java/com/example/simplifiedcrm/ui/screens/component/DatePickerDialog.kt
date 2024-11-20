package com.example.simplifiedcrm.ui.screens.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.common.extension.toCalendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    isVisibleDialog: (Boolean) -> Unit,
    onDateSelected: (Date) -> Unit
) {
    val datePickerState = rememberDatePickerState()

    BasicAlertDialog(
        onDismissRequest = { isVisibleDialog(false) }
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.medium,
            tonalElevation = AlertDialogDefaults.TonalElevation,
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                DatePicker(
                    showModeToggle = false,
                    state = datePickerState,
                    colors = DatePickerDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.background,
                    )
                )
                Button(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    onClick = {
                        val selectedDate = datePickerState.selectedDateMillis?.let { millis ->
                            Date(millis).toCalendar()
                        }
                        selectedDate?.let(onDateSelected)
                        isVisibleDialog(false)
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.select_date),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}