package com.example.simplifiedcrm.ui.screens.onboarding.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.data.model.User
import com.example.simplifiedcrm.ui.screens.onboarding.OnboardingEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterDialog(
    isRegisterDialog:(Boolean) -> Unit,
    user: User,
    event: OnboardingEvent,
    error: MutableState<Boolean>
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    BasicAlertDialog(onDismissRequest = {
        isRegisterDialog(false)
        event.resetUser()
        error.value = false
    }) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.medium,
            tonalElevation = AlertDialogDefaults.TonalElevation,
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = user.name,
                    onValueChange = {
                        scope.launch {
                            event.updateName(it)
                        }
                        error.value = false
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.name),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    isError = error.value
                )
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
                    isError = error.value
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
                    isError = error.value
                )
                Spacer(modifier = Modifier.size(10.dp))
                Button(
                    onClick = { event.registerNewUser(context) },
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = stringResource(id = R.string.register),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}