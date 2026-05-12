package io.github.devcrocod.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.devcrocod.theme.tokens
import io.github.devcrocod.theme.type

@Composable
fun Eyebrow(
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = "↳",
            style = type.eyebrow,
            color = tokens.fg1,
        )
        Text(
            text = text.uppercase(),
            style = type.eyebrow,
            color = tokens.fg1,
        )
    }
}
