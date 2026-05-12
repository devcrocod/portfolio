package io.github.devcrocod.chrome

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import io.github.devcrocod.components.PillLink
import io.github.devcrocod.components.interaction.collectIsActiveAsState
import io.github.devcrocod.components.interaction.rememberInteractiveSource
import io.github.devcrocod.data.PrimaryNav
import io.github.devcrocod.data.SocialLinks
import io.github.devcrocod.icons.PortfolioIcons
import io.github.devcrocod.nav.Route
import io.github.devcrocod.platform.openExternalUrl
import io.github.devcrocod.theme.*

@Composable
fun MobileNavDrawer(
    open: Boolean,
    currentRoute: Route,
    onNavigate: (Route) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backdropAlpha by animateFloatAsState(
        targetValue = if (open) 0.5f else 0f,
        animationSpec = tween(DurState, easing = PortfolioEasing),
        label = "drawer-backdrop",
    )
    val panelProgress by animateFloatAsState(
        targetValue = if (open) 1f else 0f,
        animationSpec = tween(DurState, easing = PortfolioEasing),
        label = "drawer-panel",
    )

    if (backdropAlpha <= 0.001f && panelProgress <= 0.001f) return

    Box(modifier = modifier.fillMaxSize()) {
        // Backdrop — captures taps to dismiss
        val backdropSource = remember { MutableInteractionSource() }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = backdropAlpha))
                .clickable(
                    interactionSource = backdropSource,
                    indication = null,
                    onClick = onDismiss,
                ),
        )
        // Panel — slides in from right
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .widthIn(min = 280.dp, max = 360.dp)
                .fillMaxWidth(0.85f)
                .graphicsLayer {
                    translationX = size.width * (1f - panelProgress)
                }
                .background(color = tokens.paper)
                .border(width = 1.dp, color = tokens.border),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                ) {
                    CloseButton(onClick = onDismiss)
                }

                Spacer(Modifier.height(24.dp))

                PrimaryNav.forEachIndexed { index, entry ->
                    if (index > 0) Spacer(Modifier.height(20.dp))
                    DrawerNavItem(
                        label = entry.label.uppercase(),
                        selected = currentRoute == entry.route,
                        onClick = { onNavigate(entry.route) },
                    )
                }

                Spacer(Modifier.height(32.dp))
                HorizontalDivider(color = tokens.border)
                Spacer(Modifier.height(32.dp))

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    PillLink(
                        text = "github",
                        onClick = { openExternalUrl(SocialLinks.GitHub) },
                    )
                    PillLink(
                        text = "linkedin",
                        onClick = { openExternalUrl(SocialLinks.LinkedIn) },
                    )
                }
            }
        }
    }
}

@Composable
private fun DrawerNavItem(label: String, selected: Boolean, onClick: () -> Unit) {
    val interactionSource = rememberInteractiveSource()
    val active by interactionSource.collectIsActiveAsState()
    val targetColor = when {
        active -> KotlinViolet
        selected -> tokens.fg1
        else -> tokens.fg2
    }
    val color by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(DurState, easing = PortfolioEasing),
        label = "drawer-item-color",
    )
    Text(
        text = label,
        style = type.cardTitleLg,
        color = color,
        modifier = Modifier
            .fillMaxWidth()
            .hoverable(interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .pointerHoverIcon(PointerIcon.Hand)
            .padding(vertical = 8.dp),
    )
}

@Composable
private fun CloseButton(onClick: () -> Unit) {
    val interactionSource = rememberInteractiveSource()
    val active by interactionSource.collectIsActiveAsState()
    val tint by animateColorAsState(
        targetValue = if (active) KotlinViolet else tokens.fg2,
        animationSpec = tween(DurState, easing = PortfolioEasing),
        label = "close-tint",
    )
    Box(
        modifier = Modifier
            .size(Spacing.TouchTargetMin)
            .hoverable(interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .pointerHoverIcon(PointerIcon.Hand),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = PortfolioIcons.Close,
            contentDescription = "Close menu",
            tint = tint,
            modifier = Modifier.size(20.dp),
        )
    }
}
