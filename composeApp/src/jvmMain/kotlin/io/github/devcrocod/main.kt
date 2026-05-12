package io.github.devcrocod

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Portfolio (dev)",
        state = rememberWindowState(width = 1280.dp, height = 900.dp),
    ) {
        App()
    }
}
