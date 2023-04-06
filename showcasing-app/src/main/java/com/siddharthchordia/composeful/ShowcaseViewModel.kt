package com.siddharthchordia.composeful

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import composables.ComponentConfiguration
import composables.ComponentDetailsInfo
import composables.ComponentSnapshotInfo
import composables.ComponentType
import composables.ExpandableCard
import composables.SearchBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class ShowcaseViewModel : ViewModel() {

    // Categories for the navigation drawer
    val categories = mutableStateListOf(
        "Basic Components",
        "Layouts",
        "Input Controls",
        "Containers",
        "Advanced Components"
    )

    private val _searchQuery = MutableStateFlow("")
    val filteredComponents: Flow<List<ComponentSnapshotInfo>> = _searchQuery.map { searchQuery ->
        val filtered = if (searchQuery.isBlank()) {
            snapshotComponents
        } else {
            snapshotComponents.filter { component ->
                component.componentName.contains(searchQuery, ignoreCase = true)
            }
        }
        filtered
    }

    val favoriteUpdates = MutableSharedFlow<ComponentSnapshotInfo>()

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    suspend fun addToFavorites(component: ComponentSnapshotInfo) {
        component.isFavorite = true
        favoriteUpdates.emit(component.copy())
    }

    suspend fun removeFromFavorites(component: ComponentSnapshotInfo) {
        component.isFavorite = false
        favoriteUpdates.emit(component.copy())
    }

    fun getFilteredComponents(searchQuery: String): List<ComponentSnapshotInfo> {
        return if (searchQuery.isBlank()) {
            snapshotComponents
        } else {
            snapshotComponents.filter { component ->
                component.componentName.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    private fun updateComponentInList(component: ComponentSnapshotInfo): ComponentSnapshotInfo {
        val index = snapshotComponents.indexOfFirst { it.id == component.id }
        return if (index != -1) {
            snapshotComponents[index] = component.copy()
            return snapshotComponents[index]
        } else component.copy()
    }

    val snapshotComponents = mutableListOf(
        ComponentSnapshotInfo(
            id = 0,
            componentName = "Expandable Card",
            description = "A card that expands and collapses on click.",
            inactiveState = {
                ExpandableCard(
                    title = { Text("Collapsed Card") },
                    content = { Text("This is the card content.") },
                )
            },
            activeState = {
                ExpandableCard(
                    title = { Text("Expanded Card") },
                    content = { Text("This is the card content.") },
                )
            },
            componentType = ComponentType.WIDE
        ),
        ComponentSnapshotInfo(
            id = 1,
            componentName = "Search",
            description = "A search bar that filters components by name.",
            inactiveState = {
                Text(
                    text = "Search components...",
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.onSurface
                )
            },
            activeState = {
                SearchBar(onSearchQueryChanged = { _ -> })
            },
            componentType = ComponentType.WIDE
        )
        // Add more components here
    )

    private val components = mutableListOf(
        ComponentDetailsInfo(
            componentName = "Expandable Card",
            componentType = ComponentType.WIDE,
            id = 0,

            configurations = listOf(
                ComponentConfiguration.ExpandableCardConfiguration(
                    title = "Collapsed Card",
                    content = "This is the card content.",
                    expanded = false
                ),
                ComponentConfiguration.ExpandableCardConfiguration(
                    title = "Expanded Card",
                    content = "This is the card content.",
                    expanded = true
                )
            )
        ),
        // Add more components with their configurations here
    )
    fun getComponentDetailsInfo(componentName: String?): ComponentDetailsInfo? {
        return componentName?.let { name ->
            components.firstOrNull { it.componentName == name }
        }
    }
}
