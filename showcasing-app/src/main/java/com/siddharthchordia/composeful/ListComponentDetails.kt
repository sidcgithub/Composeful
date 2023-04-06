package com.siddharthchordia.composeful

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composables.ComponentConfiguration

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListComponentDetails(
    componentName: String?,
    component: @Composable (ComponentConfiguration) -> Unit,
    configurations: List<ComponentConfiguration>
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(configurations) { config ->
            component(config)
        }
    }
}
