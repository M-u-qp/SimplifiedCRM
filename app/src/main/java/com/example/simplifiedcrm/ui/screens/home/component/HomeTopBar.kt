package com.example.simplifiedcrm.ui.screens.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.ui.screens.component.DropDownList
import com.example.simplifiedcrm.ui.screens.component.TaskByStatusSortOrder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    sortOrderSelected: (TaskByStatusSortOrder) -> Unit,
    navigateUp: () -> Unit
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    bitmap = ImageBitmap.imageResource(R.drawable.icons8_arrow),
                    contentDescription = null,
                    modifier = Modifier.graphicsLayer(rotationZ = 180f)
                )
            }
        },
        actions = {
            var dropDownExpended by rememberSaveable {
                mutableStateOf(false)
            }
            val sortOrderList = remember { TaskByStatusSortOrder.entries }

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "sort",
                        style = MaterialTheme.typography.labelLarge
                    )
                    IconButton(onClick = { dropDownExpended = !dropDownExpended }) {
                        Icon(
                            bitmap = ImageBitmap.imageResource(R.drawable.icons8_list),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                DropDownList(
                    itemSelected = {
                        sortOrderSelected(it)
                        dropDownExpended = false
                    },
                    request = { dropDownExpended = it },
                    list = sortOrderList,
                    opened = dropDownExpended,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    )
}