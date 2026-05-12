package io.github.devcrocod.pages

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import io.github.devcrocod.components.*
import io.github.devcrocod.components.interaction.collectIsActiveAsState
import io.github.devcrocod.components.interaction.rememberInteractiveSource
import io.github.devcrocod.data.HIGHLIGHTS
import io.github.devcrocod.data.Project
import io.github.devcrocod.nav.Route
import io.github.devcrocod.platform.openExternalUrl
import io.github.devcrocod.theme.*

@Composable
fun HomePage(
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    val highlights: List<Project> = HIGHLIGHTS
    val ws = windowSize
    val featuredTopGap = if (ws.isCompact) 72.dp else 128.dp
    val cardsSpacerBottom = if (ws.isCompact) 16.dp else 32.dp

    PageContainer(modifier = modifier.padding(pageVerticalPadding())) {
        Grid(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.span(compact = 4, medium = 8, expanded = 7)) {
                Eyebrow("intro")
                Spacer(Modifier.height(18.dp))
                IntroParagraph()
            }
        }

        Spacer(Modifier.height(sectionGap()))
        Eyebrow("connect")
        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PillLink(
                text = "github",
                onClick = { openExternalUrl("https://github.com/devcrocod") },
            )
            PillLink(
                text = "linkedin",
                onClick = { openExternalUrl("https://www.linkedin.com/in/pavelgorgulov/") },
            )
        }

        Spacer(Modifier.height(featuredTopGap))

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(bottom = cardsSpacerBottom),
        ) {
            Eyebrow("featured")
            Text(text = "/", style = type.eyebrow, color = tokens.fg3)
            WorkLink(onClick = { onNavigate(Route.Work) })
        }

        Grid(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
        ) {
            for (p in highlights) {
                CodeCard(
                    project = p,
                    modifier = Modifier
                        .span(compact = 4, medium = 4, expanded = 6)
                        .fillMaxHeight(),
                    size = CardSize.Lg,
                    onClick = { onNavigate(Route.ProjectDetail(p.id)) },
                )
            }
        }
    }
}

private const val INTRO_PREFIX = "Senior software engineer at "
private const val INTRO_LINK = "JetBrains"
private const val INTRO_SUFFIX = ", on the Kotlin team. I build frameworks and libraries in Kotlin. Multiplatform, open-source."

@Composable
private fun IntroParagraph() {
    var layout by remember { mutableStateOf<TextLayoutResult?>(null) }
    var pointerOverLink by remember { mutableStateOf(false) }

    val annotated = remember {
        buildAnnotatedString {
            append(INTRO_PREFIX)
            withLink(
                LinkAnnotation.Clickable(
                    tag = "jetbrains",
                    styles = TextLinkStyles(style = SpanStyle()),
                    linkInteractionListener = { openExternalUrl("https://www.jetbrains.com") },
                ),
            ) {
                append(INTRO_LINK)
            }
            append(INTRO_SUFFIX)
        }
    }

    val linkStart = INTRO_PREFIX.length
    val linkEndExclusive = linkStart + INTRO_LINK.length

    val linkSegments = remember(layout) {
        layout?.let { linkSegments(it, linkStart, linkEndExclusive) }.orEmpty()
    }

    val progress by animateFloatAsState(
        targetValue = if (pointerOverLink) 1f else 0f,
        animationSpec = tween(durationMillis = DurState, easing = PortfolioEasing),
        label = "jetbrains-underline-progress",
    )

    val textColor = tokens.fg1

    Text(
        text = annotated,
        style = type.heroH1,
        color = textColor,
        onTextLayout = { layout = it },
        modifier = Modifier
            .pointerInput(linkSegments) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Exit -> pointerOverLink = false
                            PointerEventType.Enter, PointerEventType.Move -> {
                                val pos = event.changes.firstOrNull()?.position
                                pointerOverLink = pos != null && linkSegments.any { it.contains(pos) }
                            }

                            else -> Unit
                        }
                    }
                }
            }
            .drawWithContent {
                drawContent()
                if (linkSegments.isEmpty()) return@drawWithContent
                val stroke = 1.5.dp.toPx()
                val yOffset = 2.dp.toPx()
                for (seg in linkSegments) {
                    val y = seg.bottom - yOffset
                    drawLine(
                        color = textColor,
                        start = Offset(seg.left, y),
                        end = Offset(seg.right, y),
                        strokeWidth = stroke,
                    )
                }
                if (progress > 0f) {
                    val totalWidth = linkSegments.fold(0f) { acc, r -> acc + (r.right - r.left) }
                    val first = linkSegments.first()
                    val last = linkSegments.last()
                    val brush = Brush.linearGradient(
                        colors = KotlinGradientColors,
                        start = Offset(first.left, first.top),
                        end = Offset(last.right, last.bottom),
                    )
                    var remaining = totalWidth * progress
                    for (seg in linkSegments) {
                        if (remaining <= 0f) break
                        val segWidth = seg.right - seg.left
                        val drawWidth = remaining.coerceAtMost(segWidth)
                        val y = seg.bottom - yOffset
                        drawLine(
                            brush = brush,
                            start = Offset(seg.left, y),
                            end = Offset(seg.left + drawWidth, y),
                            strokeWidth = stroke,
                        )
                        remaining -= segWidth
                    }
                }
            },
    )
}

private fun linkSegments(layout: TextLayoutResult, start: Int, endExclusive: Int): List<Rect> {
    if (start >= endExclusive) return emptyList()
    val out = mutableListOf<Rect>()
    for (i in start until endExclusive) {
        val box = layout.getBoundingBox(i)
        val last = out.lastOrNull()
        if (last != null && last.top == box.top && last.bottom == box.bottom) {
            out[out.lastIndex] = Rect(
                left = minOf(last.left, box.left),
                top = last.top,
                right = maxOf(last.right, box.right),
                bottom = last.bottom,
            )
        } else {
            out.add(box)
        }
    }
    return out
}

@Composable
private fun WorkLink(onClick: () -> Unit) {
    val interactionSource = rememberInteractiveSource()
    val active by interactionSource.collectIsActiveAsState()
    val color by animateColorAsState(
        targetValue = if (active) KotlinViolet else tokens.fg2,
        animationSpec = tween(DurHover, easing = PortfolioEasing),
        label = "work-link-color",
    )

    DisableSelection {
        Text(
            text = "WORK →",
            style = type.eyebrow.copy(textDecoration = TextDecoration.Underline),
            color = color,
            modifier = Modifier
                .hoverable(interactionSource)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick,
                )
                .pointerHoverIcon(PointerIcon.Hand),
        )
    }
}
