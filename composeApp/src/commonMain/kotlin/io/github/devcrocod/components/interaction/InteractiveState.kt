package io.github.devcrocod.components.interaction

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

@Composable
fun rememberInteractiveSource(): MutableInteractionSource = remember { MutableInteractionSource() }

@Composable
fun MutableInteractionSource.collectIsActiveAsState(): State<Boolean> {
    val hovered = collectIsHoveredAsState()
    val pressed = collectIsPressedAsState()
    return remember(hovered, pressed) {
        derivedStateOf { hovered.value || pressed.value }
    }
}
