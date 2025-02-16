package org.example.cross.card.auth.presentation.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerMode
import io.github.vinceglb.filekit.core.PickerType
import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.coroutines.launch
import org.example.cross.card.auth.presentation.components.UpdateNameDialog
import org.example.cross.card.core.presentation.components.ThemeSwitch
import org.example.cross.card.core.presentation.components.imageLoader


@Composable
fun ProfileScreen(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.verticalScroll(rememberScrollState())) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {

                    Box(
                        modifier = Modifier.size(100.dp)
                    ) {
                        SubcomposeAsyncImage(
                            model = state.user?.profilePicture,
                            contentDescription = "Profile Picture",
                            imageLoader = imageLoader(),
                            modifier = Modifier.fillMaxSize().padding(8.dp).clip(CircleShape),
                            error = {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = null,
                                    modifier = Modifier.size(50.dp).padding(4.dp)
                                )
                            },
                            loading = {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            },
                            contentScale = ContentScale.Crop,
                        )
                    }

                    Column {
                        Text(
                            text = state.user?.name ?: "Anonymous",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (state.user?.email.isNullOrEmpty()) "Unknown" else state.user?.email!!,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Theme", style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                ThemeSwitch()
            }
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Edit Name", style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = { onEvent(ProfileEvent.EditeNameDialog(true)) },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit, contentDescription = "Edit Name"
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Edit Profile Picture", style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = { onEvent(ProfileEvent.EditeProfilePictureDialog(true)) },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Image,
                        contentDescription = "Edit Profile Picture"
                    )
                }
            }
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

        AnimatedVisibility(
            state.profilePictureLoading,
            modifier = Modifier.align(Alignment.Center)
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun UpdateProfilePictureDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: (PlatformFile) -> Unit,
) {
    val coroutine = rememberCoroutineScope()

    var file by remember { mutableStateOf<PlatformFile?>(null) }
    var byteArray by remember { mutableStateOf<ByteArray?>(null) }

    val launcher = rememberFilePickerLauncher(
        mode = PickerMode.Single, type = PickerType.Image
    ) {
        file = it
        coroutine.launch {
            byteArray = it?.readBytes()
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = byteArray,
                    contentDescription = null,
                    imageLoader = imageLoader(),
                    modifier = Modifier.size(200.dp).padding(16.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )

                Button(onClick = { launcher.launch() }) {
                    Text("Pick Image")
                }

                Button(
                    onClick = {
                        file?.let {
                            onConfirm(it)
                        }
                    },
                    enabled = file != null
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}

