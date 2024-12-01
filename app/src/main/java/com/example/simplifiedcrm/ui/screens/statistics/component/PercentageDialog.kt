package com.example.simplifiedcrm.ui.screens.statistics.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.simplifiedcrm.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PercentageDialog(
    isVisibleDialog: (Boolean) -> Unit,
    onClick: (Float) -> Unit,
    selectedPercentage: Float
) {
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
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedPercentage.toString(),
                    onValueChange = {
                        onClick(it.toFloat())
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.percentage_sales),
                            style = MaterialTheme.typography.titleSmall
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            isVisibleDialog(false)
                        }
                    )
                )
            }
        }
    }
}