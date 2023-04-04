package com.siddharthchordia.composeful

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import composables.ComponentInfo
import composables.ComponentSnapshot
import composables.ExpandableCard
import theme.FancyDarkTheme

@Composable
fun Home(navController: NavController) {

    val components = listOf(
        ComponentInfo(
            componentName = "Expandable Card",
            description = "A card that expands and collapses on click.",
            inactiveState = {
                ExpandableCard(
                    title = { Text("Collapsed Card") },
                    content = { Text("This is the card content.") },
                    expanded = false,
                    onClick = {}
                )
            },
            activeState = {
                ExpandableCard(
                    title = { Text("Expanded Card") },
                    content = { Text("This is the card content.") },
                    expanded = true,
                    onClick = {}
                )
            }
        )
        // Add more components here
    )
    FancyDarkTheme {
        Surface(color = MaterialTheme.colors.background) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(components.chunked(2)) { _, rowItems ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rowItems.forEach { component ->
                            ComponentSnapshot(
                                componentName = component.componentName,
                                description = component.description,
                                inactiveState = component.inactiveState,
                                activeState = component.activeState,
                                modifier = Modifier.weight(1f).clickable {
                                    navController.navigate(Screen.ComponentDetails.createRoute(component.componentName))

                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
