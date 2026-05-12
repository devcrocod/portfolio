package io.github.devcrocod.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.devcrocod.components.interaction.collectIsActiveAsState
import io.github.devcrocod.components.interaction.rememberInteractiveSource
import io.github.devcrocod.data.Project
import io.github.devcrocod.icons.PortfolioIcons
import io.github.devcrocod.theme.*

enum class CardSize { Md, Lg }

@Composable
fun CodeCard(
    project: Project,
    modifier: Modifier = Modifier,
    size: CardSize = CardSize.Md,
    onClick: () -> Unit,
) {
    val interactionSource = rememberInteractiveSource()
    val active by interactionSource.collectIsActiveAsState()
    val borderColor by animateColorAsState(
        targetValue = if (active) tokens.borderStrong else tokens.border,
        animationSpec = tween(DurState, easing = PortfolioEasing),
        label = "card-border",
    )
    val translateY by animateDpAsState(
        targetValue = if (active) (-4).dp else 0.dp,
        animationSpec = tween(DurState, easing = PortfolioEasing),
        label = "card-translate",
    )
    val glow by animateFloatAsState(
        targetValue = if (active) 1f else 0f,
        animationSpec = tween(DurState, easing = PortfolioEasing),
        label = "card-glow",
    )

    val big = size == CardSize.Lg
    val padX = if (big) 22.dp else 18.dp
    val titleStyle = if (big) type.cardTitleLg else type.cardTitleMd
    val descStyle = type.proseBody

    val leftLabel = buildString {
        append(project.year)
        val st = project.status
        if (st != null) {
            append(" · ")
            append(st.name)
        }
    }.uppercase()

    Column(
        modifier = modifier
            .graphicsLayer { translationY = translateY.toPx() }
            .drawBehind {
                if (glow <= 0f) return@drawBehind
                val corner = 12.dp.toPx()
                val haloMax = 10.dp.toPx()
                val steps = 8
                val w = this.size.width
                val h = this.size.height
                val strokeW = 2.dp.toPx()
                for (i in 0 until steps) {
                    val t = i.toFloat() / (steps - 1)
                    val pad = haloMax * t
                    val a = 0.15f * glow * (1f - t) * (1f - t)
                    drawRoundRect(
                        color = KotlinViolet.copy(alpha = a),
                        topLeft = Offset(-pad, -pad),
                        size = Size(w + 2f * pad, h + 2f * pad),
                        cornerRadius = CornerRadius(corner + pad),
                        style = Stroke(width = strokeW),
                    )
                }
            }
            .clip(ShapeCard)
            .background(color = tokens.paper, shape = ShapeCard)
            .border(width = 1.dp, color = borderColor, shape = ShapeCard)
            .hoverable(interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .pointerHoverIcon(PointerIcon.Hand),
    ) {
        DisableSelection {
            // 1) Header row: title (left) · kind (right) — baseline-aligned so the kind tag sits on the title's baseline
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = padX,
                        end = padX,
                        top = if (big) 18.dp else 16.dp,
                        bottom = if (big) 14.dp else 12.dp,
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = project.title,
                    style = titleStyle,
                    color = tokens.fg1,
                    modifier = Modifier
                        .alignBy(FirstBaseline)
                        .padding(end = 16.dp),
                )
                Text(
                    text = project.kind.uppercase(),
                    style = type.cardKind,
                    color = tokens.fg3,
                    modifier = Modifier.alignBy(FirstBaseline),
                )
            }
            // 2) Code snippet
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = if (big) 18.dp else 14.dp, vertical = if (big) 14.dp else 12.dp),
            ) {
                CodeSurface(
                    snippet = project.snippet,
                    accent = project.accent,
                    modifier = Modifier.fillMaxWidth(),
                    big = big,
                )
            }
            // 3) Description
            Text(
                text = project.desc,
                style = descStyle,
                color = tokens.fg2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = padX, end = padX, bottom = if (big) 16.dp else 14.dp),
            )
        }
        // 3.5) Push footer to the bottom when the card is stretched to the row's max height
        Spacer(modifier = Modifier.weight(1f))
        // 4) Footer — single-line on both sides so card heights stay aligned
        HorizontalDivider(thickness = 1.dp, color = tokens.border)
        DisableSelection {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = padX,
                        end = padX,
                        top = if (big) 12.dp else 10.dp,
                        bottom = if (big) 16.dp else 14.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = leftLabel,
                    style = type.cardFooter,
                    color = tokens.fg3,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp),
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    when {
                        !project.stars.isNullOrBlank() -> Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            Icon(
                                painter = PortfolioIcons.Star,
                                contentDescription = null,
                                tint = tokens.fg3,
                                modifier = Modifier.size(11.dp),
                            )
                            Text(
                                text = project.stars.uppercase(),
                                style = type.cardFooter,
                                color = tokens.fg3,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }

                        project.repo.isNotBlank() -> Text(
                            text = "${project.repo} ↗".uppercase(),
                            style = type.cardFooter,
                            color = tokens.fg3,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.End,
                        )
                    }
                }
            }
        }
    }
}
