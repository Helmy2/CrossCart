package org.example.cross.card.auth.presentation.register

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import crosscart.composeapp.generated.resources.create_account
import crosscart.composeapp.generated.resources.email
import crosscart.composeapp.generated.resources.ic_create_back
import crosscart.composeapp.generated.resources.ic_create_front
import crosscart.composeapp.generated.resources.name
import crosscart.composeapp.generated.resources.no_account
import crosscart.composeapp.generated.resources.register
import org.example.cross.card.auth.domain.entity.PasswordStrength
import org.example.cross.card.auth.presentation.components.AuthButton
import org.example.cross.card.auth.presentation.components.AuthHeader
import org.example.cross.card.auth.presentation.components.AuthPasswordField
import org.example.cross.card.auth.presentation.components.AuthTextButton
import org.example.cross.card.auth.presentation.components.AuthTextField
import org.example.cross.card.auth.presentation.components.PasswordStrengthIndicator
import org.jetbrains.compose.resources.stringResource

@Composable
fun RegisterScreen(
    state: RegisterState, onEvent: (RegisterEvent) -> Unit, modifier: Modifier = Modifier
) {
    val focus = LocalFocusManager.current

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthHeader(
            imageFront = Res.drawable.ic_create_front,
            imageBack = Res.drawable.ic_create_back,
            title = Res.string.create_account,
            body = Res.string.no_account,
            modifier = Modifier.size(200.dp),
        )

        AuthTextField(
            value = state.name,
            label = stringResource(Res.string.name),
            error = state.nameError,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            onValueChange = { onEvent(RegisterEvent.NameChanged(it)) },
            modifier = Modifier.fillMaxWidth()
        )

        AuthTextField(
            value = state.email,
            label = stringResource(Res.string.email),
            error = state.emailError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
            ),
            onValueChange = { onEvent(RegisterEvent.EmailChanged(it)) },
            modifier = Modifier.fillMaxWidth()
        )

        AuthPasswordField(value = state.password,
            error = state.passwordError,
            isVisible = state.isPasswordVisible,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
            ),
            onValueChange = { onEvent(RegisterEvent.PasswordChanged(it)) },
            onVisibilityToggle = { onEvent(RegisterEvent.TogglePasswordVisibility) },
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
                AnimatedContent(state.passwordError != null) {
                    if (it) {
                        Text(
                            text = if (state.passwordError != null) stringResource(state.passwordError) else "",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelSmall
                        )
                    } else {
                        AnimatedVisibility(
                            state.passwordStrength != PasswordStrength.STRONG && state.password.isNotEmpty()
                        ) {
                            PasswordStrengthIndicator(
                                strength = state.passwordStrength,
                                requirements = state.passwordRequirements,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            })

        AuthButton(
            isLoading = state.isLoading,
            text = stringResource(Res.string.register),
            onClick = {
                focus.clearFocus()
                onEvent(RegisterEvent.Register)
            },
            modifier = Modifier.fillMaxWidth(),
        )


        AuthTextButton(
            onClick = { onEvent(RegisterEvent.NavigateToLogin) },
            modifier = Modifier,
            content = {
                Text(
                    stringResource(Res.string.already_have_account),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
        )
    }
}