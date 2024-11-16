package com.example.simplifiedcrm.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.data.local.database.entity.Task
import com.example.simplifiedcrm.ui.screens.component.TaskInfoDialog
import com.example.simplifiedcrm.ui.screens.component.TaskItemList
import com.example.simplifiedcrm.ui.screens.component.TaskTopBar

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val taskList = viewModel.taskList.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 8.dp,
                        ambientColor = MaterialTheme.colorScheme.onSurface,
                        spotColor = MaterialTheme.colorScheme.onSurface
                    )
            ) {
                TaskTopBar(
                    title = stringResource(id = R.string.current_tasks),
                    icon = R.drawable.icons8_menu
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            HomeScreenContent(
                modifier = Modifier.fillMaxWidth(),
                tasks = taskList,
                onClick = viewModel::setDialog
            )
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
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    tasks: LazyPagingItems<Task>,
    onClick: (Task) -> Unit
) {
    TaskItemList(
        modifier = modifier,
        tasks = tasks,
        onClick = onClick
    )
}