package com.example.simplifiedcrm.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.simplifiedcrm.ui.screens.component.TaskInfoDialog
import com.example.simplifiedcrm.ui.screens.component.TaskItemList
import com.example.simplifiedcrm.ui.screens.component.TaskTopBar
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    navigateToSettings: () -> Unit,
    paddingValues: PaddingValues,
    event: HomeEvent
) {
    val context = LocalContext.current
    val taskList = viewModel.taskList.collectAsLazyPagingItems()
    var dropDownExpended by rememberSaveable { mutableStateOf(false) }
    val signOut = stringResource(id = R.string.sign_out)
    val settings = stringResource(id = R.string.settings)
    val sortOrderList = remember { mutableListOf(signOut, settings) }

    val isSignIn = viewModel.navigateToLogin.collectAsState().value
    LaunchedEffect(key1 = isSignIn) {
        if (isSignIn) {
            navigateToLogin()
            viewModel.resetNavigation()
        }
    }

    val isSettings = viewModel.navigateToSettings.collectAsState().value
    LaunchedEffect(key1 = isSettings) {
        if (isSettings) {
            navigateToSettings()
            viewModel.resetNavigation()
        }
    }

    LaunchedEffect(key1 = Unit) {
        val tasks = (0 until taskList.itemCount).mapNotNull { index ->
            taskList[index]?.let { task ->
                async { viewModel.checkTasksStatus(context, task) }
            }
        }
        tasks.awaitAll()
    }

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
                    title = stringResource(id = R.string.current_tasks),
                    actionIcon = R.drawable.icons8_menu,
                    clickActionIcon = { dropDownExpended = !dropDownExpended },
                    externalDropDownExpended = dropDownExpended,
                    innerDropDownExpended = { dropDownExpended = it },
                    sortOrderList = sortOrderList,
                    sortOrderSelected = viewModel::setSelectedSortOrder,
                    onClickSelectedItemDropDownList = { viewModel.dropDownItemSelected(context) }
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
            HomeScreenContent(
                modifier = Modifier.fillMaxWidth(),
                tasks = taskList,
                onClick = viewModel::setTaskDialog,
                paddingValues = paddingValues,
                onDelete = { event.deleteTask(it) },
                onFinish = { event.finishTask(it) }
            )
        }

        viewModel.taskDialog.collectAsState().value?.let {
            TaskInfoDialog(
                isVisibleDialog = {
                    viewModel.setTaskDialog(null)
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
    onClick: (Task) -> Unit,
    paddingValues: PaddingValues,
    onDelete: (Task) -> Unit,
    onFinish: (Task) -> Unit
) {
    TaskItemList(
        modifier = modifier,
        paddingValues = paddingValues,
        tasks = tasks,
        onClick = onClick,
        onDelete = onDelete,
        onFinish = onFinish
    )
}