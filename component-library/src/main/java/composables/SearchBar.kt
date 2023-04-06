package composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun SearchBar(onSearchQueryChanged: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { newQuery ->
            searchQuery = newQuery
            onSearchQueryChanged(newQuery)
        },
        label = { Text("Search components") },
        modifier = Modifier.fillMaxWidth()
    )
}