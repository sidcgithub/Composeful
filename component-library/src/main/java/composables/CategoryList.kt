package composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CategoryList(categories: List<String>, onCategorySelected: (String) -> Unit) {
    LazyColumn {
        items(categories) { category ->
            Text(
                text = category,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCategorySelected(category) }
                    .padding(16.dp)
            )
        }
    }
}