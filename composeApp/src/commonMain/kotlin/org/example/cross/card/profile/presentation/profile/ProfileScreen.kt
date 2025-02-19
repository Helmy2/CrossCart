package org.example.cross.card.profile.presentation.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import org.example.cross.card.core.presentation.components.AdaptivePane
import org.example.cross.card.core.presentation.components.ThemeSwitch
import org.example.cross.card.core.presentation.components.imageLoader
import org.example.cross.card.core.presentation.components.shimmerEffect
import org.example.cross.card.profile.presentation.components.UpdateNameDialog
import org.example.cross.card.profile.presentation.components.UpdateProfilePictureDialog


@Composable
fun ProfileScreen(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
        ) {
            AdaptivePane(firstPane = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Box(
                        modifier = Modifier.size(80.dp)
                            .align(Alignment.CenterHorizontally)
                            .shimmerEffect(state.profilePictureLoading)
                    ) {
                        SubcomposeAsyncImage(
                            model = state.user?.profilePicture,
                            contentDescription = "Profile Picture",
                            imageLoader = imageLoader(),
                            modifier = Modifier.fillMaxSize().clip(CircleShape),
                            error = {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
                                )
                            },
                            loading = {
                                Box(modifier = Modifier.fillMaxSize().shimmerEffect())
                            },
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Name", style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.padding(8.dp))
                        Text(
                            text = state.user?.name ?: "Anonymous",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Email", style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.padding(8.dp))
                        Text(
                            text = if (state.user?.email.isNullOrEmpty()) "Unknown" else state.user?.email!!,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }, secondPane = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Theme", style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.weight(1f))
                        ThemeSwitch()
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Edit Name", style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.weight(1f))
                        IconButton(
                            onClick = { onEvent(ProfileEvent.EditeNameDialog(true)) },
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Edit, contentDescription = "Edit Name"
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Edit Profile Picture",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.weight(1f))
                        IconButton(
                            onClick = { onEvent(ProfileEvent.EditeProfilePictureDialog(true)) },
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Image,
                                contentDescription = "Edit Profile Picture"
                            )
                        }
                    }
                }
            })
            Spacer(Modifier.padding(16.dp))
            Button(
                onClick = { onEvent(ProfileEvent.Logout) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Logout")
            }
        }

        AnimatedVisibility(state.showEditNameDialog) {
            UpdateNameDialog(
                name = state.name,
                onValueChange = { onEvent(ProfileEvent.UpdateName(it)) },
                onConfirm = { onEvent(ProfileEvent.ConfirmUpdateName) },
                onDismiss = { onEvent(ProfileEvent.EditeNameDialog(false)) },
                modifier = Modifier.padding(16.dp)
            )
        }

        AnimatedVisibility(state.showEditProfilePictureDialog) {
            UpdateProfilePictureDialog(
                onConfirm = { onEvent(ProfileEvent.ConfirmUpdateProfilePicture(it)) },
                onDismiss = { onEvent(ProfileEvent.EditeProfilePictureDialog(false)) },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

