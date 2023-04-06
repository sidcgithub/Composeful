package composables

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NavigationDrawer(
    categories: List<String>,
    onCategorySelected: (String) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        CategoryList(categories = categories, onCategorySelected = onCategorySelected)
    }
}
