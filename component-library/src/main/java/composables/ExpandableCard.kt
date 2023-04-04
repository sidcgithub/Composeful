package composables

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.animateContentSize

@Composable
fun ExpandableCard(
    title: @Composable () -> Unit,
    content: @Composable () -> Unit,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val cardElevation by animateDpAsState(
        targetValue = if (expanded) 200.dp else 4.dp,
        animationSpec = tween(durationMillis = 500)
    )

    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = cardElevation
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .animateContentSize(
                    animationSpec = tween(durationMillis = 500)
                )
        ) {
            title()
            Spacer(modifier = Modifier.height(8.dp))
            if (expanded) {
                Divider()
                Spacer(modifier = Modifier.height(8.dp))
                content()
            }
        }
    }
}