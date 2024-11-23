package com.example.simplifiedcrm.ui.screens.tasks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.data.local.database.entity.Task
import com.example.simplifiedcrm.ui.screens.component.TaskByStatusSortOrder
import com.example.simplifiedcrm.ui.screens.component.TaskInfoDialog
import com.example.simplifiedcrm.ui.screens.component.TaskItemList
import com.example.simplifiedcrm.ui.screens.component.TaskTopBar

@Composable
fun TasksScreen(
    viewModel: TasksViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    event: TasksEvent
) {

    val taskList = viewModel.taskList.collectAsLazyPagingItems()
    val context = LocalContext.current
    var dropDownExpended by rememberSaveable {
        mutableStateOf(false)
    }
    val sortOrderList =
        remember { TaskByStatusSortOrder.entries }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 16.dp,
                        ambientColor = MaterialTheme.colorScheme.onSurface,
                        spotColor = MaterialTheme.colorScheme.onSurface
                    )
            ) {
                TaskTopBar(
                    title = stringResource(id = R.string.tasks),
                    actionIcon = R.drawable.icons8_list,
                    clickActionIcon = { dropDownExpended = !dropDownExpended },
                    externalDropDownExpended = dropDownExpended,
                    innerDropDownExpended = { dropDownExpended = it },
                    sortOrderList = sortOrderList,
                    sortOrderSelected = viewModel::setSortOrder,
                    stringTransform = { item -> item.getStringResource(context) }
                )
            }
        }
    ) { innerValues ->
        Box(
            modifier = Modifier
                .padding(innerValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            TasksScreenContent(
                modifier = Modifier.fillMaxWidth(),
                tasks = taskList,
                onClick = viewModel::setDialog,
                paddingValues = paddingValues,
                onDelete = { event.deleteTask(it) }
            )
        }
    }

    viewModel.dialog.collectAsState().value?.let {
        TaskInfoDialog(
            isVisibleDialog = {
                viewModel.setDialog(null)
            },
            task = it
        )
    }
}

@Composable
private fun TasksScreenContent(
    modifier: Modifier = Modifier,
    tasks: LazyPagingItems<Task>,
    onClick: (Task) -> Unit,
    paddingValues: PaddingValues,
    onDelete: (Task) -> Unit
) {
    TaskItemList(
        modifier = modifier,
        tasks = tasks,
        onClick = onClick,
        paddingValues = paddingValues,
        onDelete = onDelete,
        onFinish = {}
    )
}