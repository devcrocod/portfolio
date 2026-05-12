package io.github.devcrocod.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import io.github.devcrocod.theme.tokens
import io.github.devcrocod.theme.type

@Composable
fun MetaItem(
    label: String,
    modifier: Modifier = Modifier,
    labelStyle: TextStyle = type.cardKind,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        DisableSelection {
            Text(
                text = label.uppercase(),
                style = labelStyle,
                color = tokens.fg3,
            )
        }
        content()
    }
}
