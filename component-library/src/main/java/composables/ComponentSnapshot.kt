package composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ComponentSnapshot(
    componentName: String,
    description: String,
    inactiveState: @Composable () -> Unit,
    activeState: @Composable () -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier,
    componentType: ComponentType = ComponentType.WIDE,
    componentSize: Modifier
) {
    Card(
        modifier = modifier
            .then(componentSize)
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp
    ) {
        val componentWidth = when (componentType) {
            ComponentType.LONG -> 0.4f
            ComponentType.WIDE -> 1f
            ComponentType.LARGE -> 0.6f
        }
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            IconButton(
                onClick = onToggleFavorite,
                modifier = Modifier.align(alignment = End)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites"
                )
            }
            Text(
                text = componentName,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Box(
                    modifier = Modifier
                        .weight(componentWidth)
                        .padding(end = 8.dp)
                ) {
                    inactiveState()
                }
                Box(
                    modifier = Modifier
                        .weight(componentWidth)
                        .padding(start = 8.dp)
                ) {
                    activeState()
                }
            }
        }
    }

}

enum class ComponentType {
    LONG, WIDE, LARGE
}


data class ComponentSnapshotInfo(
    val id: Int,
    val componentName: String,
    val description: String,
    val inactiveState: @Composable () -> Unit,
    val activeState: @Composable () -> Unit,
    var isFavorite: Boolean = false,
    val componentType: ComponentType,
)

data class ComponentDetailsInfo(
    val id: Int,
    val componentName: String,
    val componentType: ComponentType,
    val configurations: List<ComponentConfiguration>
)


sealed class ComponentConfiguration {
    data class ExpandableCardConfiguration(
        val title: String,
        val content: String,
        var expanded: Boolean
    ) : ComponentConfiguration()

    // Add more subclasses for other components as needed
}