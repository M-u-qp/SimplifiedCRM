package com.example.simplifiedcrm.ui.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simplifiedcrm.R

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit
) {
    val isSignIn = viewModel.navigateToLogin.collectAsState().value
    LaunchedEffect(key1 = isSignIn) {
        if (!isSignIn) {
            navigateToLogin()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.profile),
                style = MaterialTheme.typography.titleSmall
            )
            Button(onClick = {
                viewModel.signOut()
            }) {
                Text(
                    text = stringResource(id = R.string.sign_out),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}