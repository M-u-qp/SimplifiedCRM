package com.example.simplifiedcrm.ui.screens.task_creation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource

@Composable
fun TaskButton(
    modifier: Modifier = Modifier,
    text: Int,
    @DrawableRes icon: Int,
    isExpanded: (Boolean) -> Unit
) {
    val isExpandedState = remember { mutableStateOf(false) }

    OutlinedButton(
        modifier = modifier.fillMaxWidth(),
        onClick = {
            isExpandedState.value = !isExpandedState.value
            isExpanded(isExpandedState.value)
        },
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = text),
                style = MaterialTheme.typography.bodyMedium
            )
            Icon(
                bitmap = ImageBitmap.imageResource(id = icon),
                contentDescription = null
            )
        }
    }
}