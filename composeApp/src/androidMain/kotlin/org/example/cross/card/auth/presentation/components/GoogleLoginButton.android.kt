package org.example.cross.card.auth.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import crosscart.composeapp.generated.resources.Res
import crosscart.composeapp.generated.resources.ic_google
import crosscart.composeapp.generated.resources.loginWithGoogle
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
actual fun GoogleLoginButton(
    loading: Boolean,
    onAuthResult: (result: Result<Unit>) -> Unit,
    modifier: Modifier
) {
    val supabase: SupabaseClient = koinInject()

    val action = supabase.composeAuth.rememberSignInWithGoogle(
        onResult = { result ->
            when (result) {
                NativeSignInResult.ClosedByUser -> {}
                is NativeSignInResult.Error -> {
                    onAuthResult(Result.failure(result.exception?.cause ?: Exception()))
                }

                is NativeSignInResult.NetworkError -> {
                    onAuthResult(Result.failure(Exception(result.message)))
                }

                NativeSignInResult.Success -> {
                    onAuthResult(Result.success(Unit))
                }
            }
        }
    )

    Button(
        onClick = {
            action.startFlow()
        },
        modifier = modifier.height(50.dp),
        enabled = !loading,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        AnimatedContent(
            targetState = loading,
        ) { loading ->
            if (loading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(4.dp)
                )
            } else {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_google),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(4.dp)
                    )
                    Text(
                        text = stringResource(Res.string.loginWithGoogle),
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}