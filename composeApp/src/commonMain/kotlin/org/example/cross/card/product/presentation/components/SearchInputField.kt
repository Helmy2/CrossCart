package org.example.cross.card.product.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchInputField(
    query: String,
    expanded: Boolean,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBarDefaults.InputField(
        modifier = modifier,
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        placeholder = { Text("Search") },
        leadingIcon = {
            AnimatedContent(expanded) { expanded ->
                if (expanded) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                } else {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                }
            }
        },
        trailingIcon = {
            AnimatedVisibility(expanded) {
                IconButton(onClick = onFilterClick) {
                    Icon(
                        Icons.Default.Tune,
                        contentDescription = "Filter Icon"
                    )
                }
            }
        }
    )
}