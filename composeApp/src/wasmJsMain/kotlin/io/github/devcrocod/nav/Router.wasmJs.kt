@file:OptIn(ExperimentalWasmJsInterop::class)

package io.github.devcrocod.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.browser.window
import org.w3c.dom.events.Event

@Composable
actual fun rememberRouter(): RouterState {
    val routeState = remember { mutableStateOf(parsePath(window.location.pathname)) }

    DisposableEffect(Unit) {
        val listener: (Event) -> Unit = {
            val parsed = parsePath(window.location.pathname)
            if (parsed != routeState.value) routeState.value = parsed
        }
        window.addEventListener("popstate", listener)
        onDispose {
            window.removeEventListener("popstate", listener)
        }
    }

    return remember(routeState) {
        RouterState(
            routeState = routeState,
            navigate = { next ->
                if (routeState.value != next) {
                    routeState.value = next
                    val newPath = next.path
                    if (window.location.pathname != newPath) {
                        window.history.pushState(null, "", newPath)
                    }
                }
            },
            goBack = { window.history.back() },
        )
    }
}
