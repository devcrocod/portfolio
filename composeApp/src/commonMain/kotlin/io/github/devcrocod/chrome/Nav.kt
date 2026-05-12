package io.github.devcrocod.chrome

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.blur.blurEffect
import dev.chrisbanes.haze.blur.materials.HazeMaterials
import dev.chrisbanes.haze.hazeEffect
import io.github.devcrocod.components.interaction.collectIsActiveAsState
import io.github.devcrocod.components.interaction.rememberInteractiveSource
import io.github.devcrocod.icons.PortfolioIcons
import io.github.devcrocod.nav.Route
import io.github.devcrocod.theme.*

val NavHeight = 72.dp

@Composable
fun NavBackdrop(
    modifier: Modifier = Modifier,
    hazeState: HazeState? = null,
) {
    val paper = tokens.paper
    val borderColor = tokens.border
    val hazeStyle = HazeMaterials.thin()
    val surface = if (hazeState != null) {
        Modifier.hazeEffect(state = hazeState) {
            blurEffect {
                style = hazeStyle
                blurRadius = 98.dp
                noiseFactor = 0.3f
                backgroundColor = Color.Transparent
            }
        }
    } else {
        Modifier.background(color = paper.copy(alpha = 0.7f))
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(NavHeight)
            .then(surface)
            .drawBehind {
                drawLine(
                    color = borderColor,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 1f,
                )
            },
    )
}

@Composable
fun Nav(
    currentRoute: Route,
    onNavigate: (Route) -> Unit,
    isDark: Boolean,
    onToggleDark: () -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val ws = windowSize
    val edge = edgePadding()
    val showHamburger = ws.isCompact
    val navItemGap = ws.pick(20.dp, 32.dp, 36.dp)
    val toggleGap = ws.pick(16.dp, 24.dp, 28.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(NavHeight),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .widthIn(max = Spacing.MaxContentWidth)
                .fillMaxWidth()
                .padding(horizontal = edge),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BrandLink(onClick = { onNavigate(Route.Home) })
            Spacer(Modifier.weight(1f))

            if (showHamburger) {
                ThemeToggle(isDark = isDark, onClick = onToggleDark, sizeDp = 44.dp)
                Spacer(Modifier.size(toggleGap))
                MenuButton(onClick = onMenuClick)
            } else {
                NavItem(label = "work", selected = currentRoute is Route.Work, onClick = { onNavigate(Route.Work) })
                Spacer(Modifier.size(navItemGap))
                NavItem(label = "info", selected = currentRoute is Route.About, onClick = { onNavigate(Route.About) })
                Spacer(Modifier.size(toggleGap))
                ThemeToggle(isDark = isDark, onClick = onToggleDark, sizeDp = 36.dp)
            }
        }
    }
}

@Composable
private fun BrandLink(onClick: () -> Unit) {
    val interactionSource = rememberInteractiveSource()
    val active by interactionSource.collectIsActiveAsState()
    val textColor by animateColorAsState(
        targetValue = if (active) KotlinViolet else tokens.fg1,
        animationSpec = tween(DurState, easing = PortfolioEasing),
        label = "brand-text-color",
    )
    val brandText = if (windowSize.isCompact) "P. GORGULOV" else "PAVEL GORGULOV"

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .hoverable(interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .pointerHoverIcon(PointerIcon.Hand),
    ) {
        Crossfade(
            targetState = active,
            animationSpec = tween(DurState, easing = PortfolioEasing),
            label = "brand-mark",
        ) { isActive ->
            Image(
                painter = if (isActive) PortfolioIcons.KotlinMarkPrimary else PortfolioIcons.KotlinMarkMono,
                contentDescription = "Kotlin",
                modifier = Modifier.size(14.dp),
            )
        }
        Text(
            text = brandText,
            style = type.brandName,
            color = textColor,
        )
    }
}

@Composable
private fun NavItem(label: String, selected: Boolean, onClick: () -> Unit) {
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
        label = "nav-item-color",
    )
    val progress by animateFloatAsState(
        targetValue = if (selected || active) 1f else 0f,
        animationSpec = tween(DurState, easing = PortfolioEasing),
        label = "nav-item-underline",
    )

    Text(
        text = label.uppercase(),
        style = type.navItem,
        color = color,
        modifier = Modifier
            .hoverable(interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .pointerHoverIcon(PointerIcon.Hand)
            .drawBehind {
                if (progress <= 0f) return@drawBehind
                val y = size.height - 4.dp.toPx()
                drawLine(
                    color = color,
                    start = Offset(0f, y),
                    end = Offset(size.width * progress, y),
                    strokeWidth = 1.5.dp.toPx(),
                )
            }
            .padding(vertical = 8.dp),
    )
}

@Composable
private fun ThemeToggle(isDark: Boolean, onClick: () -> Unit, sizeDp: androidx.compose.ui.unit.Dp = 36.dp) {
    val interactionSource = rememberInteractiveSource()
    val active by interactionSource.collectIsActiveAsState()
    val tint by animateColorAsState(
        targetValue = if (active) KotlinViolet else tokens.fg2,
        animationSpec = tween(DurHover, easing = PortfolioEasing),
        label = "theme-toggle-tint",
    )
    val glow by animateFloatAsState(
        targetValue = if (active) 1f else 0f,
        animationSpec = tween(DurState, easing = PortfolioEasing),
        label = "theme-toggle-glow",
    )
    Box(
        modifier = Modifier
            .size(sizeDp)
            .drawBehind {
                if (glow <= 0f) return@drawBehind
                val radius = size.minDimension * 0.5f
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            KotlinViolet.copy(alpha = 0.32f * glow),
                            KotlinViolet.copy(alpha = 0.10f * glow),
                            Color.Transparent,
                        ),
                        center = center,
                        radius = radius,
                    ),
                    center = center,
                    radius = radius,
                )
            }
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
            painter = if (isDark) PortfolioIcons.Sun else PortfolioIcons.Moon,
            contentDescription = if (isDark) "Switch to light theme" else "Switch to dark theme",
            tint = tint,
            modifier = Modifier.size(16.dp),
        )
    }
}

@Composable
private fun MenuButton(onClick: () -> Unit) {
    val interactionSource = rememberInteractiveSource()
    val active by interactionSource.collectIsActiveAsState()
    val tint by animateColorAsState(
        targetValue = if (active) KotlinViolet else tokens.fg1,
        animationSpec = tween(DurHover, easing = PortfolioEasing),
        label = "menu-tint",
    )
    Box(
        modifier = Modifier
            .size(44.dp)
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
            painter = PortfolioIcons.Menu,
            contentDescription = "Open menu",
            tint = tint,
            modifier = Modifier.size(22.dp),
        )
    }
}
