package com.example.simplifiedcrm.ui.screens.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.ui.screens.component.TaskTopBar

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToStatistics: () -> Unit
) {
    val isSignIn = viewModel.navigateToLogin.collectAsState().value
    LaunchedEffect(key1 = isSignIn) {
        if (isSignIn) {
            navigateToLogin()
        }
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
                    title = stringResource(id = R.string.profile),
                    actionIcon = R.drawable.icons8_edit,
                    sortOrderList = listOf()
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
            ProfileContent(
                navigateToSettings = navigateToSettings,
                navigateToStatistics = navigateToStatistics
            )
        }
    }
}

@Composable
private fun ProfileContent(
    navigateToSettings: () -> Unit,
    navigateToStatistics: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProfileItem(
            modifier = Modifier.fillMaxWidth(),
            icon = R.drawable.icons8_settings,
            itemText = R.string.settings,
            onClick = navigateToSettings
        )
        ProfileItem(
            modifier = Modifier.fillMaxWidth(),
            icon = R.drawable.icons8_statistic,
            itemText = R.string.statistics,
            onClick = navigateToStatistics
        )
    }
}

@Composable
private fun ProfileItem(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    itemText: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium
            )
            .clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(10.dp),
            bitmap = ImageBitmap.imageResource(icon),
            contentDescription = null
        )
        Text(
            text = stringResource(id = itemText),
            style = MaterialTheme.typography.titleMedium
        )
    }
}