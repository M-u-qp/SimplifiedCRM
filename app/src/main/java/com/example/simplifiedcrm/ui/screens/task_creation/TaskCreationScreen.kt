package com.example.simplifiedcrm.ui.screens.task_creation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.data.local.database.entity.Task
import com.example.simplifiedcrm.ui.screens.component.TaskTopBar
import kotlinx.coroutines.launch

@Composable
fun TaskCreationScreen(
    viewModel: TaskCreationViewModel = hiltViewModel(),
    event: TaskCreationEvent,
    navigateToHome: () -> Unit,
    navigateUp: () -> Unit
) {
    val task = viewModel.task.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TaskTopBar(
            navigateUp = navigateUp,
            title = stringResource(id = R.string.creating_task)
        )
        TaskCreationContent(
            task = task,
            event = event,
            navigateToHome = navigateToHome
        )
    }
}

@Composable
private fun TaskCreationContent(
    task: Task,
    event: TaskCreationEvent,
    navigateToHome: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val error = remember { mutableStateOf(false) }
    val isExpandedClient = remember { mutableStateOf(false) }
    val isExpandedProduct = remember { mutableStateOf(false) }
    val isExpandedDelivery = remember { mutableStateOf(false) }
    val isExpandedDescription = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        OutlinedButton(
            onClick = { isExpandedClient.value = !isExpandedClient.value },
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = stringResource(id = R.string.client),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (isExpandedClient.value) {
            ElevatedCard(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.primary
                    ),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
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
                        value = task.client.name,
                        onValueChange = {
                            scope.launch {
                                event.updateClientName(name = task.client.name)
                            }
                            error.value = false
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.FCs),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        isError = error.value
                    )
                    OutlinedTextField(
                        value = task.client.phone,
                        onValueChange = {
                            scope.launch {
                                event.updateClientPhone(phone = task.client.phone)
                            }
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.phone_number),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )
                    OutlinedTextField(
                        value = task.client.email,
                        onValueChange = {
                            scope.launch {
                                event.updateClientEmail(email = task.client.email)
                            }
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.email),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )
                    OutlinedTextField(
                        value = task.client.marking,
                        onValueChange = {
                            scope.launch {
                                event.updateClientMarking(marking = task.client.marking)
                            }
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.marking),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )
                }
            }
        }

        OutlinedButton(
            onClick = { isExpandedProduct.value = !isExpandedProduct.value },
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = stringResource(id = R.string.product),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (isExpandedProduct.value) {
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
                        value = task.productName,
                        onValueChange = {
                            scope.launch {
                                event.updateProductName(productName = task.productName)
                            }
                            error.value = false
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.product_name),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        isError = error.value
                    )
                    OutlinedTextField(
                        value = task.productPrice.toString(),
                        onValueChange = {
                            scope.launch {
                                event.updateProductPrice(productPrice = task.productPrice)
                            }
                            error.value = false
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.product_price),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        isError = error.value
                    )
                }
            }
        }

        OutlinedButton(
            onClick = { isExpandedDelivery.value = !isExpandedDelivery.value },
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = stringResource(id = R.string.delivery),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (isExpandedDelivery.value) {
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
                        value = task.delivery.name,
                        onValueChange = {
                            scope.launch {
                                event.updateDeliveryName(name = task.delivery.name)
                            }
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.delivery_name),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )
                    OutlinedTextField(
                        value = task.delivery.price.toString(),
                        onValueChange = {
                            scope.launch {
                                event.updateDeliveryPrice(price = task.delivery.price)
                            }
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.delivery_price),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )
                }
            }
        }

        OutlinedButton(
            onClick = { isExpandedDescription.value = !isExpandedDescription.value },
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = stringResource(id = R.string.marking),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (isExpandedDescription.value) {
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
                        value = task.description,
                        onValueChange = {
                            scope.launch {
                                event.updateDescription(description = task.description)
                            }
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.description_task),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(25.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navigateToHome() },
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = stringResource(id = R.string.create_task),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}