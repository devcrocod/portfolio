package io.github.devcrocod.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember

@Composable
fun PortfolioTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit,
) {
    val tokens = remember(darkTheme) { if (darkTheme) darkTokens() else lightTokens() }
    val plexSans = plexSansFamily()
    val plexMono = plexMonoFamily()
    val jbMono = monoFamily()
    val ws = LocalWindowSize.current
    val type = remember(plexSans, plexMono, jbMono, ws) {
        portfolioType(plexSans, plexMono, jbMono, ws)
    }
    val m3Typography = remember(plexSans, ws) { portfolioM3Typography(plexSans, ws) }

    val colorScheme = remember(darkTheme, tokens) {
        val base = if (darkTheme) darkColorScheme() else lightColorScheme()
        base.copy(
            background = tokens.paper,
            surface = tokens.paper,
            surfaceVariant = tokens.bgSubtle,
            onBackground = tokens.fg1,
            onSurface = tokens.fg1,
            onSurfaceVariant = tokens.fg2,
            outline = tokens.border,
            outlineVariant = tokens.borderStrong,
            primary = tokens.ink,
            onPrimary = tokens.paper,
            secondary = tokens.n400,
            error = tokens.danger,
        )
    }

    CompositionLocalProvider(
        LocalPortfolioTokens provides tokens,
        LocalPortfolioType provides type,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = m3Typography,
            shapes = PortfolioShapes,
            content = content,
        )
    }
}

val tokens: PortfolioColors
    @Composable @ReadOnlyComposable
    get() = LocalPortfolioTokens.current

val type: PortfolioType
    @Composable @ReadOnlyComposable
    get() = LocalPortfolioType.current
