package io.github.devcrocod.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class WindowSize { Compact, Medium, Expanded }

val LocalWindowSize = staticCompositionLocalOf { WindowSize.Expanded }

val windowSize: WindowSize
    @Composable @ReadOnlyComposable
    get() = LocalWindowSize.current

inline val WindowSize.isCompact: Boolean get() = this == WindowSize.Compact
inline val WindowSize.isExpanded: Boolean get() = this == WindowSize.Expanded

fun Dp.toWindowSize(): WindowSize = when {
    this < 600.dp -> WindowSize.Compact
    this < 905.dp -> WindowSize.Medium
    else -> WindowSize.Expanded
}

fun <T> WindowSize.pick(compact: T, medium: T, expanded: T): T = when (this) {
    WindowSize.Compact -> compact
    WindowSize.Medium -> medium
    WindowSize.Expanded -> expanded
}
