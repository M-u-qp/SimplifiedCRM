package com.example.simplifiedcrm.ui.screens.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.simplifiedcrm.data.local.database.entity.Task

@Composable
fun TaskItemList(
    modifier: Modifier = Modifier,
    tasks: LazyPagingItems<Task>,
    onClick: (Task) -> Unit,
    paddingValues: PaddingValues,
    onTaskChecked: (Task) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(0.dp),
        contentPadding = paddingValues
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
                    onTaskChecked(task)
                    TaskCard(
                        task = task,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .padding(vertical = 6.dp)
                            .fillMaxWidth()
                            .clickable { onClick(task) },
                        shape = RoundedCornerShape(
                            topStart = 0.dp, topEnd = 12.dp,
                            bottomStart = 0.dp, bottomEnd = 12.dp
                        )
                    )
                }
            }
        }
    }
}