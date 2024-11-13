package com.example.simplifiedcrm.ui.screens.component

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.imageResource
import com.example.simplifiedcrm.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTopBar(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    title: String
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.icons8_arrow),
                    contentDescription = null,
                    modifier = Modifier.graphicsLayer(rotationZ = 180f)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
    )
}