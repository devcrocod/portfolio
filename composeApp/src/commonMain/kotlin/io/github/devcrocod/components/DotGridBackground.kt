package io.github.devcrocod.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.dotGrid(
    spacing: Dp = 24.dp,
    dotRadius: Dp = 0.5.dp,
    color: Color = Color(0x14FFFFFF),
): Modifier = this.drawBehind {
    val sp = spacing.toPx()
    val r = dotRadius.toPx().coerceAtLeast(0.5f)
    var x = 0f
    while (x < size.width) {
        var y = 0f
        while (y < size.height) {
            drawCircle(color = color, radius = r, center = Offset(x, y))
            y += sp
        }
        x += sp
    }
}

fun Modifier.lineGrid(
    spacing: Dp = 32.dp,
    lineWidth: Dp = 1.dp,
    color: Color = Color(0x0AFFFFFF),
): Modifier = this.drawBehind {
    val sp = spacing.toPx()
    val w = lineWidth.toPx().coerceAtLeast(1f)
    var x = sp
    while (x < size.width) {
        drawLine(
            color = color,
            start = Offset(x, 0f),
            end = Offset(x, size.height),
            strokeWidth = w,
        )
        x += sp
    }
    var y = sp
    while (y < size.height) {
        drawLine(
            color = color,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = w,
        )
        y += sp
    }
}
