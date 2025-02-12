package org.example.cross.card.auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import crosscart.composeapp.generated.resources.Res
import crosscart.composeapp.generated.resources.continue_as_guest
import crosscart.composeapp.generated.resources.email
import crosscart.composeapp.generated.resources.forgot_password
import crosscart.composeapp.generated.resources.ic_welcome_back
import crosscart.composeapp.generated.resources.ic_welcome_front
import crosscart.composeapp.generated.resources.login
import crosscart.composeapp.generated.resources.login_to_your_account
import crosscart.composeapp.generated.resources.no_account
import crosscart.composeapp.generated.resources.welcome_back
import org.example.cross.card.auth.presentation.components.AuthButton
import org.example.cross.card.auth.presentation.components.AuthHeader
import org.example.cross.card.auth.presentation.components.AuthPasswordField
import org.example.cross.card.auth.presentation.components.AuthTextButton
import org.example.cross.card.auth.presentation.components.AuthTextField
import org.example.cross.card.core.presentation.components.AdaptivePane
import org.example.cross.card.core.presentation.components.IconLabelRow
import org.jetbrains.compose.resources.stringResource


@Composable
fun LoginScreen(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focus = LocalFocusManager.current
    AdaptivePane(
        modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        firstPane = {
            AuthHeader(
                imageFront = Res.drawable.ic_welcome_front,
                imageBack = Res.drawable.ic_welcome_back,
                title = Res.string.welcome_back,
                body = Res.string.login_to_your_account,
                modifier = Modifier.size(300.dp).padding(16.dp),
            )
        },
        secondPane = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.sizeIn(maxWidth = 600.dp)
            ) {
                AuthTextField(
                    value = state.email,
                    label = stringResource(Res.string.email),
                    error = state.emailError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                    ),
                    onValueChange = { onEvent(LoginEvent.EmailChanged(it)) },
                    modifier = Modifier.fillMaxWidth()
                )

                AuthPasswordField(
                    value = state.password,
                    error = state.passwordError,
                    isVisible = state.isPasswordVisible,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                    onValueChange = { onEvent(LoginEvent.PasswordChanged(it)) },
                    onVisibilityToggle = { onEvent(LoginEvent.TogglePasswordVisibility) },
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = if (state.passwordError != null) {
                        {
                            Text(
                                text = stringResource(state.passwordError),
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.labelSmall,
                            )
                        }
                    } else {
                        null
                    },
                )

                AuthTextButton(
                    onClick = { onEvent(LoginEvent.NavigateToRestPassword) },
                    modifier = Modifier.align(alignment = Alignment.End),
                    content = {
                        IconLabelRow(
                            imageVector = Icons.Outlined.Lock,
                            text = Res.string.forgot_password,
                        )
                    },
                )

                AuthButton(
                    isLoading = state.isLoading,
                    text = stringResource(Res.string.login),
                    onClick = {
                        focus.clearFocus()
                        onEvent(LoginEvent.Login)
                    },
                    modifier = Modifier.fillMaxWidth(),
                )

                AuthTextButton(
                    onClick = { onEvent(LoginEvent.SignInAnonymously) },
                    content = {
                        IconLabelRow(
                            imageVector = Icons.Outlined.Person,
                            text = Res.string.continue_as_guest,
                        )
                    },
                )

                AuthTextButton(
                    onClick = { onEvent(LoginEvent.NavigateToRegister) },
                    content = {
                        Text(
                            stringResource(Res.string.no_account),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                )
            }
        },
    )
}

