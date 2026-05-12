package io.github.devcrocod.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.devcrocod.theme.*

@Composable
fun CodeSurface(
    snippet: String,
    accent: Color = KotlinViolet,
    modifier: Modifier = Modifier,
    big: Boolean = false,
) {
    val isDark = tokens.isDark
    val annotated = remember(snippet, isDark) {
        kotlinTokens(snippet, if (isDark) CodeDarkPalette else CodeLightPalette)
    }
    val ws = windowSize
    val padX = if (big) 26.dp else 22.dp
    val padY = if (big) 22.dp else 18.dp
    val codeStyle = if (big) type.code else type.codeSm
    val scrollNarrow = !ws.isExpanded

    val bg = if (isDark) CodeDarkPalette.bg else CodeLightPalette.bg
    val fg = if (isDark) CodeDarkPalette.fg else CodeLightPalette.fg
    val gridColor = if (isDark) CodeDarkPalette.grid else CodeLightPalette.grid
    val accentAlpha = if (isDark) 0.25f else 0.20f

    val baseModifier = modifier
        .clip(ShapeCodeSurface)
        .background(color = bg, shape = ShapeCodeSurface)

    val borderedModifier = if (isDark) {
        baseModifier
    } else {
        baseModifier.border(width = 1.dp, color = CodeLightPalette.accentBorder, shape = ShapeCodeSurface)
    }

    Box(modifier = borderedModifier) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .lineGrid(spacing = 32.dp, lineWidth = 1.dp, color = gridColor),
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .drawBehind {
                    val r = 220.dp.toPx() / 2f
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(accent.copy(alpha = accentAlpha), Color.Transparent),
                            center = Offset(size.width - 80.dp.toPx() + r, -80.dp.toPx() + r),
                            radius = r,
                        ),
                        radius = r,
                        center = Offset(size.width - 80.dp.toPx() + r, -80.dp.toPx() + r),
                    )
                },
        )
        val codeText: @Composable () -> Unit = {
            Text(
                text = annotated,
                style = codeStyle,
                color = fg,
                softWrap = !scrollNarrow,
                modifier = Modifier.padding(horizontal = padX, vertical = padY),
            )
        }
        if (scrollNarrow) {
            Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                codeText()
            }
        } else {
            codeText()
        }
    }
}
