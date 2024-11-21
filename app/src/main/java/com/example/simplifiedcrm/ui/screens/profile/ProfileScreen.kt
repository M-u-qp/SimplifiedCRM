package com.example.simplifiedcrm.ui.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.ui.screens.component.TaskTopBar

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit
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
            Column {

            }
        }
    }
}