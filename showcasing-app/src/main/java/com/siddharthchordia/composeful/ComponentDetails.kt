package com.siddharthchordia.composeful

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composables.ExpandableCard
import theme.FancyDarkTheme

@Composable
fun ComponentDetails() {
    FancyDarkTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column {
                val items = listOf(
                    "Item 1" to "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ut purus vitae augue fringilla fringilla.",
                    "Item 2" to "Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Vivamus id nisl id sem consequat consequat.",
                    "Item 3" to "Nunc ullamcorper, turpis sit amet facilisis interdum, enim ante iaculis urna, eu sollicitudin quam justo sed mi.",
                    "Item 4" to "Curabitur nec arcu nec quam auctor iaculis eget vitae dui. Suspendisse potenti.",
                    "Item 5" to "Integer commodo lorem eu neque bibendum, vitae varius neque sagittis."
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    items.forEachIndexed { index, (itemTitle, itemContent) ->
                        val expandedState = remember { mutableStateOf(false) }
                        ExpandableCard(
                            title = { Text(itemTitle, style = MaterialTheme.typography.h6) },
                            content = { Text(itemContent) },
                            expanded = expandedState.value,
                            onClick = { expandedState.value = !expandedState.value }
                        )
                    }
                }
            }
        }
    }

}