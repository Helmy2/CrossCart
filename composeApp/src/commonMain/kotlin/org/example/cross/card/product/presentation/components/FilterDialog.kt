package org.example.cross.card.product.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.example.cross.card.product.domain.entity.OrderBy
import org.example.cross.card.product.presentation.home.Filter

@Composable
fun FilterDialog(
    onDismiss: () -> Unit,
    onFilterChange: (Filter) -> Unit,
    onOrderByChange: (OrderBy) -> Unit,
    onConfirm: () -> Unit,
    filter: Filter,
    orderBy: OrderBy,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(modifier.clip(MaterialTheme.shapes.large)) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SortingSection(
                    orderBy = orderBy,
                    onOrderByChange = onOrderByChange,
                )
                FilterSection(
                    filter = filter,
                    onFilterChange = onFilterChange,
                )
                Button(
                    onClick = {
                        onDismiss()
                        onConfirm()
                    }, modifier = Modifier.align(Alignment.End).padding(8.dp)
                ) {
                    Text(text = "Apply")
                }
            }
        }
    }

}