package io.github.devcrocod.chrome

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import io.github.devcrocod.components.Grid
import io.github.devcrocod.components.PageContainer
import io.github.devcrocod.components.PillLink
import io.github.devcrocod.components.interaction.collectIsActiveAsState
import io.github.devcrocod.components.interaction.rememberInteractiveSource
import io.github.devcrocod.icons.PortfolioIcons
import io.github.devcrocod.nav.Route
import io.github.devcrocod.platform.openExternalUrl
import io.github.devcrocod.theme.*
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

private val currentYear: Int = Clock.System.now()
    .toLocalDateTime(TimeZone.currentSystemDefault())
    .year

@Composable
fun Footer(onNavigate: (Route) -> Unit, modifier: Modifier = Modifier) {
    val ws = windowSize
    val verticalPad = if (ws.isCompact) 24.dp else 40.dp
    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(thickness = 1.dp, color = tokens.border)
        PageContainer(modifier = Modifier.padding(top = verticalPad, bottom = verticalPad)) {
            if (ws.isCompact) {
                FooterCompact(onNavigate = onNavigate)
            } else {
                FooterRegular(onNavigate = onNavigate)
            }
        }
    }
}

@Composable
private fun FooterCompact(onNavigate: (Route) -> Unit) {
    Grid(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Column(
            modifier = Modifier.span(compact = 2, medium = 2, expanded = 2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            FooterNavItem("WORK") { onNavigate(Route.Work) }
            FooterNavItem("INFO") { onNavigate(Route.About) }
        }
        Column(
            modifier = Modifier.span(compact = 2, medium = 2, expanded = 2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PillLink(
                text = "github",
                onClick = { openExternalUrl("https://github.com/devcrocod") },
            )
            PillLink(
                text = "linkedin",
                onClick = { openExternalUrl("https://www.linkedin.com/in/pavelgorgulov/") },
            )
        }
    }

    Spacer(Modifier.height(40.dp))

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "© Pavel Gorgulov · $currentYear",
            style = type.eyebrow,
            color = tokens.fg2,
        )
        BuiltWithRow()
    }
}

@Composable
private fun FooterRegular(onNavigate: (Route) -> Unit) {
    Grid(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Column(
            modifier = Modifier.span(compact = 4, medium = 8, expanded = 6),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "© Pavel Gorgulov · $currentYear",
                style = type.eyebrow,
                color = tokens.fg1,
            )
            BuiltWithRow()
        }
        Column(
            modifier = Modifier.span(compact = 4, medium = 4, expanded = 3),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            FooterNavItem("WORK") { onNavigate(Route.Work) }
            FooterNavItem("INFO") { onNavigate(Route.About) }
        }
        Column(
            modifier = Modifier.span(compact = 4, medium = 4, expanded = 3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PillLink(
                text = "github",
                onClick = { openExternalUrl("https://github.com/devcrocod") },
            )
            PillLink(
                text = "linkedin",
                onClick = { openExternalUrl("https://www.linkedin.com/in/pavelgorgulov/") },
            )
        }
    }
}

@Composable
private fun BuiltWithRow() {
    val interactionSource = rememberInteractiveSource()
    val active by interactionSource.collectIsActiveAsState()
    val textColor by animateColorAsState(
        targetValue = if (active) KotlinViolet else tokens.fg2,
        animationSpec = tween(DurState, easing = PortfolioEasing),
        label = "built-with-text-color",
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.hoverable(interactionSource),
    ) {
        Text(
            text = "Built with",
            style = type.eyebrow,
            color = textColor,
        )
        Crossfade(
            targetState = active,
            animationSpec = tween(DurState, easing = PortfolioEasing),
            label = "compose-mark",
        ) { isActive ->
            Image(
                painter = if (isActive) PortfolioIcons.ComposeMultiplatformMarkPrimary else PortfolioIcons.ComposeMultiplatformMark,
                contentDescription = "Compose Multiplatform",
                modifier = Modifier.size(16.dp),
            )
        }
        Text(
            text = "Compose Web",
            style = type.eyebrow,
            color = textColor,
        )
    }
}

@Composable
private fun FooterNavItem(label: String, onClick: () -> Unit) {
    val interactionSource = rememberInteractiveSource()
    val active by interactionSource.collectIsActiveAsState()
    val color by animateColorAsState(
        targetValue = if (active) KotlinViolet else tokens.fg2,
        animationSpec = tween(durationMillis = DurState, easing = PortfolioEasing),
    )
    DisableSelection {
        Text(
            text = label,
            style = type.eyebrow,
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
