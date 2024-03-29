package com.siddharthchordia.composeful

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import composables.ComponentSnapshot
import composables.ComponentType
import kotlinx.coroutines.launch

@Composable
fun Home(navController: NavController, showcaseViewModel: ShowcaseViewModel) {
    val filteredComponents by showcaseViewModel.filteredComponents.collectAsState(emptyList())
    val coroutineScope = rememberCoroutineScope()
    val favoriteUpdate by showcaseViewModel.favoriteUpdates.collectAsState(null)

    Surface(color = MaterialTheme.colors.background) {
        val configuration = LocalConfiguration.current
        val screenWidthDp = with(LocalDensity.current) {
            (configuration.screenWidthDp * density).toInt().dp
        }
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredComponents) { component ->
                val componentSize = when (component.componentType) {
                    ComponentType.LONG -> Modifier.size(width = screenWidthDp * 0.5f, height = screenWidthDp * 0.5f)
                    ComponentType.WIDE -> Modifier.fillMaxWidth()
                    ComponentType.LARGE -> Modifier.size(width = screenWidthDp, height = screenWidthDp * 0.8f)
                }

                ComponentSnapshot(
                    componentName = component.componentName,
                    description = component.description,
                    inactiveState = component.inactiveState,
                    activeState = component.activeState,
                    isFavorite = if (favoriteUpdate != null && component.componentName == favoriteUpdate!!.componentName) component.isFavorite else component.isFavorite,
                    onToggleFavorite = {
                        coroutineScope.launch {
                            if (component.isFavorite) {
                                showcaseViewModel.removeFromFavorites(component)
                            } else {
                                showcaseViewModel.addToFavorites(component)
                            }
                        }
                    },
                    modifier = Modifier
                        .clickable {
                            navController.navigate(
                                Screen.ComponentDetails.createRoute(
                                    component.componentName
                                )
                            )
                        },
                    componentType = component.componentType,
                    componentSize = componentSize
                )
            }
        }
    }
}
