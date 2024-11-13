package com.example.simplifiedcrm.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.simplifiedcrm.data.local.database.entity.Task
import com.example.simplifiedcrm.ui.screens.component.TaskCard

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val taskList = viewModel.taskList.collectAsLazyPagingItems()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TaskList(
            tasks = taskList,
            onClick = {}
        )
    }
}

@Composable
fun TaskList(
    modifier: Modifier = Modifier,
    tasks: LazyPagingItems<Task>,
    onClick: (Task) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = compositionLocalOf { 0.dp }.current + 6.dp)
    ) {
        if (tasks.loadState.refresh == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        } else {
            items(tasks.itemCount) {
                tasks[it]?.let { task ->
                    TaskCard(
                        task = task,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onClick(task) }
                    )
                }
            }
        }
    }
}