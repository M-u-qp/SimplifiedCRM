package com.example.simplifiedcrm.ui.screens.task_creation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.data.local.database.entity.Task
import com.example.simplifiedcrm.ui.screens.component.TaskByStatusSortOrder
import com.example.simplifiedcrm.ui.screens.component.TaskTopBar
import com.example.simplifiedcrm.ui.screens.task_creation.component.TaskButton
import com.example.simplifiedcrm.ui.screens.task_creation.component.TaskCalendar
import com.example.simplifiedcrm.ui.screens.task_creation.component.TaskTextField
import kotlinx.coroutines.launch
import java.util.Date

@Composable
fun TaskCreationScreen(
    viewModel: TaskCreationViewModel = hiltViewModel(),
    event: TaskCreationEvent,
    navigateToHome: () -> Unit,
    navigateUp: () -> Unit
) {
    val task = viewModel.task.collectAsState().value
    val navigateState = viewModel.navigateToHome.collectAsState().value
    val context = LocalContext.current
    val error = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = navigateState) {
        if (navigateState) {
            navigateToHome()
        }
    }

    LaunchedEffect(key1 = viewModel.error.value) {
        if (viewModel.error.value.isNotBlank()) {
            Toast.makeText(context, viewModel.error.value, Toast.LENGTH_SHORT).show()
            error.value = true
            event.resetError()
        }
    }
    Scaffold(
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                onClick = {
                    event.updateTimestamp(Date(System.currentTimeMillis()))
                    event.updateStatusTask(TaskByStatusSortOrder.ACTIVE.name)
                    viewModel.createTask()
                },
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = stringResource(id = R.string.create_task),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        topBar = {
            TaskTopBar(
                clickNavigationIcon = navigateUp,
                title = stringResource(id = R.string.creating_task),
                navigationIcon = R.drawable.icons8_arrow,
                navigationIconModifier = Modifier.graphicsLayer(rotationZ = 180f),
                sortOrderList = listOf()
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            TaskCreationContent(
                modifier = Modifier.align(Alignment.Center),
                task = task,
                event = event,
                innerError = { error.value = it },
                externalError = error.value,
                onSelectedDate = { event.updateEndTime(it) }
            )
        }
    }
}

@Composable
private fun TaskCreationContent(
    modifier: Modifier = Modifier,
    task: Task,
    event: TaskCreationEvent,
    innerError: (Boolean) -> Unit = {},
    externalError: Boolean = false,
    onSelectedDate: (Date) -> Unit
) {
    val scope = rememberCoroutineScope()
    var isExpandedClient by remember { mutableStateOf(false) }
    var isExpandedProduct by remember { mutableStateOf(false) }
    var isExpandedDelivery by remember { mutableStateOf(false) }
    var isExpandedDescription by remember { mutableStateOf(false) }
    var isExpandedDate by remember { mutableStateOf(false) }
    val selectedDate = remember { mutableStateOf<Date?>(null) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = "* - " + stringResource(id = R.string.required_fields),
            style = MaterialTheme.typography.bodyMedium
        )

        TaskButton(text = R.string.expiration_date,
            icon = if (!isExpandedClient) {
                R.drawable.icons8_switch_off
            } else {
                R.drawable.icons8_switch_on
            },
            isExpanded = { isExpandedDate = it }
        )
        if (isExpandedDate) {
            TaskCalendar(
                onSelectedDate = {
                    selectedDate.value = it
                    onSelectedDate(it)
                },
                externalDate = selectedDate.value
            )
        }
        TaskButton(
            text = R.string.client,
            icon = if (!isExpandedClient) {
                R.drawable.icons8_switch_off
            } else {
                R.drawable.icons8_switch_on
            },
            isExpanded = { isExpandedClient = it }
        )
        if (isExpandedClient) {
            TaskTextField(
                event1 = { scope.launch { event.updateClientName(it) } },
                task1 = task.client.name,
                text1 = stringResource(id = R.string.FCs),
                event2 = { scope.launch { event.updateClientPhone(it) } },
                task2 = task.client.phone,
                text2 = stringResource(id = R.string.phone_number),
                keyboardOptions2 = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                event3 = { scope.launch { event.updateClientEmail(it) } },
                task3 = task.client.email,
                text3 = stringResource(id = R.string.email),
                event4 = { scope.launch { event.updateClientMarking(it) } },
                task4 = task.client.marking,
                text4 = stringResource(id = R.string.client_marking)
            )
        }

        TaskButton(
            text = R.string.product,
            icon = if (!isExpandedProduct) {
                R.drawable.icons8_switch_off
            } else {
                R.drawable.icons8_switch_on
            },
            isExpanded = { isExpandedProduct = it }
        )
        if (isExpandedProduct) {
            TaskTextField(
                event1 = { scope.launch { event.updateProductName(it) } },
                task1 = task.productName,
                text1 = "*" + stringResource(id = R.string.product_name),
                event2 = {
                    scope.launch { event.updateProductPrice(it.toLong()) }
                },
                task2 = if (task.productPrice == 0L) "" else task.productPrice.toString(),
                text2 = "*" + stringResource(id = R.string.product_price),
                keyboardOptions2 = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                externalError = externalError,
                innerError = { innerError(it) }
            )
        }

        TaskButton(
            text = R.string.delivery,
            icon = if (!isExpandedDelivery) {
                R.drawable.icons8_switch_off
            } else {
                R.drawable.icons8_switch_on
            },
            isExpanded = { isExpandedDelivery = it }
        )
        if (isExpandedDelivery) {
            TaskTextField(
                event1 = { scope.launch { event.updateDeliveryName(it) } },
                task1 = task.delivery.name,
                text1 = stringResource(id = R.string.delivery_name),
                event2 = {
                    scope.launch { event.updateDeliveryPrice(it.toLong()) }
                },
                task2 = if (task.delivery.price == 0L) "" else task.delivery.price.toString(),
                text2 = stringResource(id = R.string.delivery_price),
                keyboardOptions2 = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
        }

        TaskButton(
            text = R.string.description_task,
            icon = if (!isExpandedDescription) {
                R.drawable.icons8_switch_off
            } else {
                R.drawable.icons8_switch_on
            },
            isExpanded = { isExpandedDescription = it }
        )
        if (isExpandedDescription) {
            TaskTextField(
                event1 = { scope.launch { event.updateDescription(it) } },
                task1 = task.description,
                text1 = stringResource(id = R.string.description_task)
            )
        }
    }
}