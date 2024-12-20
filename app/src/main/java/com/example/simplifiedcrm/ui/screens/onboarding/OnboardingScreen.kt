package com.example.simplifiedcrm.ui.screens.onboarding

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.data.model.User
import com.example.simplifiedcrm.ui.screens.onboarding.component.Logo
import com.example.simplifiedcrm.ui.screens.onboarding.component.RegisterDialog
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    event: OnboardingEvent
) {
    val navigateState by viewModel.navigateToHome.collectAsState()
    var isRegisterDialog by remember { mutableStateOf(false) }
    val user = viewModel.user.collectAsState().value
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val error = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = navigateState) {
        if (navigateState) {
            navigateToHome()
        }
    }
    LaunchedEffect(key1 = viewModel.error.value) {
        if (viewModel.error.value.isNotBlank()) {
            Toast.makeText(context, viewModel.error.value, Toast.LENGTH_SHORT).show()
            error.value = true
            event.resetError()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Logo(modifier = Modifier.fillMaxWidth())
        OnboardingContent(
            modifier = Modifier.fillMaxWidth(),
            user = user,
            event = event,
            error = error,
            isRegisterDialog = { isRegisterDialog = it },
            keyboardController = keyboardController
        )
    }

    if (isRegisterDialog) {
        RegisterDialog(
            isRegisterDialog = { isRegisterDialog = it },
            error = error,
            event = event,
            user = user,
            keyboardController = keyboardController
        )
    }
}

@Composable
private fun OnboardingContent(
    modifier: Modifier = Modifier,
    user: User,
    event: OnboardingEvent,
    error: MutableState<Boolean>,
    isRegisterDialog: (Boolean) -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = user.login,
            onValueChange = {
                scope.launch {
                    event.updateLogin(it)
                }
                error.value = false
            },
            label = {
                Text(
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            isError = error.value,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )
        Spacer(modifier = Modifier.size(5.dp))
        OutlinedTextField(
            value = user.password,
            onValueChange = {
                scope.launch {
                    event.updatePassword(it)
                }
                error.value = false
            },
            label = {
                Text(
                    text = stringResource(id = R.string.password),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            isError = error.value,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )
        Spacer(modifier = Modifier.size(10.dp))
        Button(
            onClick = { event.signIn(user.login, user.password) },
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = stringResource(id = R.string.sign_in),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        TextButton(onClick = {
            isRegisterDialog(true)
            event.resetUser()
            error.value = false
        }) {
            Text(
                text = stringResource(id = R.string.register) + "?",
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}
