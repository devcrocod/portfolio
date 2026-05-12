package io.github.devcrocod.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.devcrocod.theme.Spacing
import io.github.devcrocod.theme.edgePadding

@Composable
fun PageContainer(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = Spacing.MaxContentWidth)
                .fillMaxWidth()
                .padding(horizontal = edgePadding()),
            content = content,
        )
    }
}
