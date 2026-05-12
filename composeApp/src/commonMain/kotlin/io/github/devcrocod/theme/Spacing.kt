package io.github.devcrocod.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Spacing {
    val EdgePaddingMobile = 20.dp
    val EdgePaddingSmall = 32.dp
    val EdgePaddingLarge = 48.dp
    val MaxContentWidth = 1920.dp
    val LargeBreakpoint = 800.dp
    val GridGap = 32.dp
    val GridGapCompact = 16.dp
    val GridColumnsCompact = 4
    val GridColumnsMedium = 8
    val GridColumnsLarge = 12
    val ProseMaxWidth = 720.dp

    val SectionGapMobile = 48.dp
    val SectionGapTablet = 72.dp
    val SectionGapDesktop = 96.dp

    val BlockGapMobile = 24.dp
    val BlockGapTablet = 32.dp
    val BlockGapDesktop = 40.dp
}

@Composable
@ReadOnlyComposable
fun edgePadding(): Dp = windowSize.pick(
    Spacing.EdgePaddingMobile,
    Spacing.EdgePaddingSmall,
    Spacing.EdgePaddingLarge,
)

@Composable
@ReadOnlyComposable
fun gridGap(): Dp = if (windowSize.isCompact) Spacing.GridGapCompact else Spacing.GridGap

@Composable
@ReadOnlyComposable
fun sectionGap(): Dp = windowSize.pick(
    Spacing.SectionGapMobile,
    Spacing.SectionGapTablet,
    Spacing.SectionGapDesktop,
)

@Composable
@ReadOnlyComposable
fun blockGap(): Dp = windowSize.pick(
    Spacing.BlockGapMobile,
    Spacing.BlockGapTablet,
    Spacing.BlockGapDesktop,
)

@Composable
@ReadOnlyComposable
fun pageVerticalPadding(): PaddingValues = when (windowSize) {
    WindowSize.Compact -> PaddingValues(top = 56.dp, bottom = 64.dp)
    WindowSize.Medium -> PaddingValues(top = 64.dp, bottom = 80.dp)
    WindowSize.Expanded -> PaddingValues(top = 80.dp, bottom = 96.dp)
}
