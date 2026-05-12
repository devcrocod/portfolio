package io.github.devcrocod.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import io.github.devcrocod.data.Project
import io.github.devcrocod.theme.ShapeCard
import io.github.devcrocod.theme.accentGlow
import io.github.devcrocod.theme.tokens
import io.github.devcrocod.theme.type

@Composable
fun HeroPlaceholder(
    project: Project,
    modifier: Modifier = Modifier,
    overlay: (@Composable BoxScope.() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(ShapeCard)
            .background(color = tokens.bgSubtle, shape = ShapeCard)
            .border(width = 1.dp, color = tokens.border, shape = ShapeCard),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .matchParentSize()
                .dotGrid(spacing = 24.dp, dotRadius = 0.6.dp, color = tokens.border),
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .accentGlow(
                    active = true,
                    color = project.accent,
                    innerAlpha = 0.22f,
                    midAlpha = 0.06f,
                    radius = { it.minDimension * 0.55f },
                    center = { Offset(it.width * 0.72f, it.height * 0.32f) },
                ),
        )
        if (overlay != null) {
            overlay()
        } else {
            Text(
                text = (project.coverLabel ?: project.title).uppercase(),
                style = type.cardKind.copy(letterSpacing = 0.08.em),
                color = tokens.fg3,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 28.dp, bottom = 24.dp),
            )
        }
    }
}
