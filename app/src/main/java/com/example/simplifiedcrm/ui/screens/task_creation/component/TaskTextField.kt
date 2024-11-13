package com.example.simplifiedcrm.ui.screens.task_creation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun TaskTextField(
    event1: (String) -> Unit,
    event2: (String) -> Unit = {},
    event3: (String) -> Unit = {},
    event4: (String) -> Unit = {},
    task1: String,
    task2: String = "",
    task3: String = "",
    task4: String = "",
    text1: String,
    text2: String = "",
    text3: String = "",
    text4: String = "",
    innerError: (Boolean) -> Unit = {},
    externalError: Boolean = false,
    keyboardOptions1: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
    keyboardOptions2: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
    keyboardOptions3: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
    keyboardOptions4: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
) {
    val keyboardController = LocalSoftwareKeyboardController.current

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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            OutlinedTextField(
                value = task1,
                onValueChange = {
                    event1(it)
                    innerError(false)
                },
                label = {
                    Text(
                        text = text1,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                isError = externalError,
                keyboardOptions = keyboardOptions1,
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                )
            )
            if (text2 != "") {
                OutlinedTextField(
                    value = task2,
                    onValueChange = {
                        event2(it)
                        innerError(false)
                    },
                    label = {
                        Text(
                            text = text2,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    isError = externalError,
                    keyboardOptions = keyboardOptions2,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    )
                )
            }
            if (text3 != "") {
                OutlinedTextField(
                    value = task3,
                    onValueChange = {
                        event3(it)
                        innerError(false)
                    },
                    label = {
                        Text(
                            text = text3,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    isError = externalError,
                    keyboardOptions = keyboardOptions3,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    )
                )
            }
            if (text4 != "") {
                OutlinedTextField(
                    value = task4,
                    onValueChange = {
                        event4(it)
                        innerError(false)
                    },
                    label = {
                        Text(
                            text = text4,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    isError = externalError,
                    keyboardOptions = keyboardOptions4,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    )
                )
            }
        }
    }
}