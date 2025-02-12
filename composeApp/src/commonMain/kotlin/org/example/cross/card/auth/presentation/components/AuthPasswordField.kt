package org.example.cross.card.auth.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import crosscart.composeapp.generated.resources.Res
import crosscart.composeapp.generated.resources.hide_password
import crosscart.composeapp.generated.resources.password
import crosscart.composeapp.generated.resources.requirement_met
import crosscart.composeapp.generated.resources.requirement_not_met
import crosscart.composeapp.generated.resources.show_password
import org.example.cross.card.auth.domain.entity.PasswordStrength
import org.example.cross.card.auth.domain.entity.Requirement
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun AuthPasswordField(
    value: String,
    error: StringResource?,
    isVisible: Boolean,
    supportingText: @Composable (() -> Unit)?,
    keyboardOptions: KeyboardOptions,
    onValueChange: (String) -> Unit,
    onVisibilityToggle: () -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(stringResource(Res.string.password)) },
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                onDone()
            }
        ),
        visualTransformation = if (isVisible) VisualTransformation.None
        else PasswordVisualTransformation(),
        singleLine = true,
        isError = error != null,
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = .2f),
            unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = .2f),
        ),
        trailingIcon = {
            PasswordVisibilityToggle(
                isVisible = isVisible, onToggle = onVisibilityToggle
            )
        },
        supportingText = supportingText
    )
}

@Composable
private fun PasswordVisibilityToggle(
    isVisible: Boolean, onToggle: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val icon = if (isVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff

    IconButton(
        onClick = onToggle, interactionSource = interactionSource, modifier = Modifier.size(24.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = if (isVisible) stringResource(Res.string.hide_password) else stringResource(
                Res.string.show_password
            ),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun PasswordStrengthIndicator(
    strength: PasswordStrength, requirements: List<Requirement>, modifier: Modifier = Modifier
) {
    val color = when (strength) {
        PasswordStrength.WEAK -> MaterialTheme.colorScheme.error
        PasswordStrength.MEDIUM -> MaterialTheme.colorScheme.primary
        PasswordStrength.STRONG -> MaterialTheme.colorScheme.tertiary
    }

    Column(modifier = modifier.fillMaxWidth()) {
        // Strength text and progress bar
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Password Strength: ",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = strength.toString(),
                style = MaterialTheme.typography.labelMedium,
                color = color,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Animated progress bar
        LinearProgressIndicator(
            progress = { strength.strengthValue },
            color = color,
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(50))
        )

        // Password requirements checklist
        if (requirements.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            PasswordRequirementsChecklist(requirements = requirements)
        }
    }
}

@Composable
private fun PasswordRequirementsChecklist(requirements: List<Requirement>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        requirements.forEach { requirement ->
            RequirementItem(
                text = requirement.text, isMet = requirement.isMet
            )
        }
    }
}

@Composable
private fun RequirementItem(text: StringResource, isMet: Boolean) {
    val icon = if (isMet) Icons.Default.CheckCircle else Icons.Default.Info
    val color =
        if (isMet) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant

    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = if (isMet) stringResource(Res.string.requirement_met)
            else stringResource(
                Res.string.requirement_not_met
            ),
            tint = color,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(text), style = MaterialTheme.typography.labelSmall, color = color
        )
    }
}

