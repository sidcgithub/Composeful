package com.siddharthchordia.composeful

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composables.ComponentConfiguration
import composables.ComponentDetailsInfo
import composables.ExpandableCard

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListComponentDetails(
    componentName: String?,
    componentDetailsInfo: ComponentDetailsInfo
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(componentDetailsInfo.configurations) { config ->

            when(config) {
                is ComponentConfiguration.ExpandableCardConfiguration -> ExpandableCard(title = config.title, content =
                config.content, initialExpanded = config.expanded )
                else -> Text("Work in Progress")

            }

        }
    }
}
