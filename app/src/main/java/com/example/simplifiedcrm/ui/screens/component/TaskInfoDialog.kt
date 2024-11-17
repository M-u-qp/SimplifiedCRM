package com.example.simplifiedcrm.ui.screens.component

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.common.extension.dialPhoneNumber
import com.example.simplifiedcrm.common.extension.sendToEmail
import com.example.simplifiedcrm.data.local.database.entity.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskInfoDialog(
    isVisibleDialog: (Boolean) -> Unit,
    task: Task
) {
    val context = LocalContext.current

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
                    .padding(16.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PairItemsInRow(category = R.string.FCs, item = task.client.name)
                PairItemsInRow(
                    modifier = Modifier.clickable { context.dialPhoneNumber(task.client.phone) },
                    category = R.string.phone_number,
                    item = task.client.phone,
                    colorItem = MaterialTheme.colorScheme.primary
                )
                PairItemsInRow(
                    modifier = Modifier.clickable { context.sendToEmail(task.client.email) },
                    category = R.string.email,
                    item = task.client.email,
                    colorItem = MaterialTheme.colorScheme.primary
                )
                PairItemsInRow(category = R.string.client_marking, item = task.client.marking)
                HorizontalDivider()

                PairItemsInRow(category = R.string.product_name, item = task.productName)
                PairItemsInRow(
                    category = R.string.product_price,
                    item = task.productPrice.toString(),
                    colorItem = MaterialTheme.colorScheme.error
                )
                HorizontalDivider()

                PairItemsInRow(category = R.string.delivery_name, item = task.delivery.name)
                PairItemsInRow(
                    category = R.string.delivery_price,
                    item = task.delivery.price.toString(),
                    colorItem = MaterialTheme.colorScheme.error
                )
                HorizontalDivider()

                PairItemsInRow(category = R.string.description_task, item = task.description)
            }
        }
    }
}

@Composable
private fun PairItemsInRow(
    modifier: Modifier = Modifier,
    @StringRes category: Int,
    item: String,
    colorItem: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = category) + ":",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = item,
            style = MaterialTheme.typography.bodyMedium,
            color = colorItem
        )
    }
}