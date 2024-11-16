package com.example.simplifiedcrm.ui.screens.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTopBar(
    modifier: Modifier = Modifier,
    navigate: () -> Unit = {},
    title: String = "",
    @DrawableRes icon: Int,
    iconModifier: Modifier = Modifier
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
        actions = {
            IconButton(onClick = navigate) {
                Icon(
                    bitmap = ImageBitmap.imageResource(id = icon),
                    contentDescription = null,
                    modifier = iconModifier
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
    )
}