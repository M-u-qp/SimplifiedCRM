package com.example.simplifiedcrm.ui.screens.tasks.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.ui.screens.component.DropDownList
import com.example.simplifiedcrm.ui.screens.component.TaskByStatusSortOrder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTasksTopBar(
    sortOrderSelected: (TaskByStatusSortOrder) -> Unit
) {
    val context = LocalContext.current
    var dropDownExpended by rememberSaveable {
        mutableStateOf(false)
    }
    val sortOrderList =
        remember { TaskByStatusSortOrder.entries }
    val sortOrderStrings =
        remember { TaskByStatusSortOrder.entries.map { it.getStringResource(context) } }

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.tasks),
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            Box(
                modifier = Modifier
            ) {
                IconButton(onClick = { dropDownExpended = !dropDownExpended }) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.drawable.icons8_list),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                DropDownList(
                    itemSelected = {
                        sortOrderSelected(it)
                        dropDownExpended = false
                    },
                    request = { dropDownExpended = it },
                    list = sortOrderList,
                    stringTransform = { sortOrder ->
                        sortOrderStrings[sortOrder.ordinal] },
                    opened = dropDownExpended,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}