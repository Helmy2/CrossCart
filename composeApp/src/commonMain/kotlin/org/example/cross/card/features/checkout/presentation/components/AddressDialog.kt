package org.example.cross.card.features.checkout.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.example.cross.card.features.checkout.domain.models.Address

@Composable
fun AddressDialog(
    address: Address?,
    onConfirm: (Address) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentAddress by rememberSaveable() {
        mutableStateOf(address ?: Address("", "", "", ""))
    }
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface {
            Column(
                modifier = modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(
                    value = currentAddress.street,
                    onValueChange = { currentAddress = currentAddress.copy(street = it) },
                    label = { Text("Street") },
                    modifier = Modifier.padding(8.dp)
                )
                OutlinedTextField(
                    value = currentAddress.city,
                    onValueChange = { currentAddress = currentAddress.copy(city = it) },
                    label = { Text("City") },
                    modifier = Modifier.padding(8.dp)
                )
                OutlinedTextField(
                    value = currentAddress.state,
                    onValueChange = { currentAddress = currentAddress.copy(state = it) },
                    label = { Text("State") },
                    modifier = Modifier.padding(8.dp)
                )
                OutlinedTextField(
                    value = currentAddress.country,
                    onValueChange = { currentAddress = currentAddress.copy(country = it) },
                    label = { Text("Country") },
                    modifier = Modifier.padding(8.dp)
                )
                TextButton(
                    onClick = { onConfirm(currentAddress) },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}