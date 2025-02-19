package org.example.cross.card.features.auth.presentation.resetPassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import crosscart.composeapp.generated.resources.already_have_account
import crosscart.composeapp.generated.resources.email
import crosscart.composeapp.generated.resources.enter_email_reset_password
import crosscart.composeapp.generated.resources.forgot_password
import crosscart.composeapp.generated.resources.ic_forgot_password_back
import crosscart.composeapp.generated.resources.ic_forgot_password_front
import crosscart.composeapp.generated.resources.reset_password
import org.example.cross.card.core.presentation.components.AdaptivePane
import org.example.cross.card.features.auth.presentation.components.AuthButton
import org.example.cross.card.features.auth.presentation.components.AuthHeader
import org.example.cross.card.features.auth.presentation.components.AuthTextButton
import org.example.cross.card.features.auth.presentation.components.AuthTextField
import org.jetbrains.compose.resources.stringResource


@Composable
fun RestPasswordScreen(
    state: ResetPasswordState,
    onEvent: (ResetPasswordEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val focus = LocalFocusManager.current

    AdaptivePane(
        modifier.verticalScroll(rememberScrollState()).padding(16.dp),
        firstPane = {
            AuthHeader(
                imageFront = Res.drawable.ic_forgot_password_front,
                imageBack = Res.drawable.ic_forgot_password_back,
                title = Res.string.forgot_password,
                body = Res.string.enter_email_reset_password,
                modifier = Modifier.size(300.dp).padding(16.dp),
            )
        },
        secondPane = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
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
                    onValueChange = { onEvent(ResetPasswordEvent.EmailChanged(it)) },
                    modifier = Modifier.fillMaxWidth()
                )

                AuthButton(
                    isLoading = state.isLoading,
                    text = stringResource(Res.string.reset_password),
                    onClick = {
                        focus.clearFocus()
                        onEvent(ResetPasswordEvent.ResetPassword)
                    },
                    modifier = Modifier.fillMaxWidth(),
                )

                AuthTextButton(
                    onClick = { onEvent(ResetPasswordEvent.NavigateToLogin) },
                    content = {
                        Text(
                            stringResource(Res.string.already_have_account),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                )
            }
        },
    )
}
