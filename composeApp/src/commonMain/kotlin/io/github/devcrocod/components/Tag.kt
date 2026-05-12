package io.github.devcrocod.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.devcrocod.theme.KotlinViolet
import io.github.devcrocod.theme.ShapePill
import io.github.devcrocod.theme.tokens
import io.github.devcrocod.theme.type

enum class TagSize { Sm, Md }

private data class TagSpec(val style: TextStyle, val px: Dp, val py: Dp, val dot: Dp, val gap: Dp)

@Composable
private fun TagSize.spec(): TagSpec = when (this) {
    TagSize.Sm -> TagSpec(
        style = type.tag,
        px = 10.dp,
        py = 5.dp,
        dot = 6.dp,
        gap = 6.dp,
    )

    TagSize.Md -> TagSpec(
        style = type.tag.copy(fontSize = 13.sp, lineHeight = 13.sp),
        px = 14.dp,
        py = 8.dp,
        dot = 7.dp,
        gap = 8.dp,
    )
}

@Composable
fun Tag(
    text: String,
    modifier: Modifier = Modifier,
    size: TagSize = TagSize.Sm,
    solid: Boolean = false,
    dot: Boolean = false,
    dotColor: Color = KotlinViolet,
) {
    val s = size.spec()
    val bg = if (solid) tokens.ink else Color.Transparent
    val borderColor = if (solid) tokens.ink else tokens.border
    val fg = if (solid) tokens.paper else tokens.fg2

    Row(
        modifier = modifier
            .clip(ShapePill)
            .background(color = bg, shape = ShapePill)
            .border(width = 1.dp, color = borderColor, shape = ShapePill)
            .padding(horizontal = s.px, vertical = s.py),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(s.gap),
    ) {
        if (dot) {
            Box(
                modifier = Modifier
                    .size(s.dot)
                    .clip(CircleShape)
                    .background(color = dotColor, shape = CircleShape),
            )
        }
        Text(
            text = text.uppercase(),
            style = s.style,
            color = fg,
        )
    }
}
