package org.example.cross.card.product.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.cross.card.core.util.format
import org.example.cross.card.product.presentation.home.Filter

@Composable
fun FilterSection(filter: Filter, onFilterChange: (Filter) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "From rating")
            Text(
                text = "%.2f".format(filter.rating ?: 0f),
            )
            Slider(
                value = filter.rating ?: 0f,
                onValueChange = {
                    onFilterChange(filter.copy(rating = it))
                },
                valueRange = 0f..5f,
            )
        }
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Price")
            Text(text = "%.2f$".format(filter.price.start))
            RangeSlider(
                value = filter.price,
                onValueChange = { onFilterChange(filter.copy(price = it)) },
                valueRange = filter.defaultPrice,
                modifier = Modifier.weight(1f)
            )
            Text(text = "%.2f$".format(filter.price.endInclusive))
        }
    }
}