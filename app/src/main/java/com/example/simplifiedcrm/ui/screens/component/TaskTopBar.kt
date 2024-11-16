package com.example.simplifiedcrm.ui.screens.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTopBar(
    modifier: Modifier = Modifier,
    clickNavigationIcon: () -> Unit = {},
    clickActionIcon: () -> Unit = {},
    title: String = "",
    @DrawableRes navigationIcon: Int = -1,
    @DrawableRes actionIcon: Int = -1,
    navigationIconModifier: Modifier = Modifier,
    actionIconModifier: Modifier = Modifier,
    externalDropDownExpended: Boolean = false,
    innerDropDownExpended: (Boolean) -> Unit = {},
    sortOrderList: List<String> = emptyList(),
    sortOrderSelected: (String) -> Unit = {},
    onClickSelectedItemDropDownList: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        navigationIcon = {
            if (navigationIcon != -1) {
                IconButton(onClick = clickNavigationIcon) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(id = navigationIcon),
                        contentDescription = null,
                        modifier = navigationIconModifier
                    )
                }
            }
        },
        actions = {
            Box {
                if (actionIcon != -1) {
                    IconButton(onClick = clickActionIcon) {
                        Icon(
                            bitmap = ImageBitmap.imageResource(id = actionIcon),
                            contentDescription = null,
                            modifier = actionIconModifier,
                            tint =
                            if (externalDropDownExpended) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                DropDownList(
                    itemSelected = {
                        sortOrderSelected(it)
                        innerDropDownExpended(false)
                        onClickSelectedItemDropDownList()
                    },
                    request = {
                        innerDropDownExpended(it)
                    },
                    list = sortOrderList,
                    opened = externalDropDownExpended,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
    )
}