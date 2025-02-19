package org.example.cross.card.auth.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerMode
import io.github.vinceglb.filekit.core.PickerType
import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.coroutines.launch
import org.example.cross.card.core.presentation.components.imageLoader

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
                    }, enabled = file != null
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}