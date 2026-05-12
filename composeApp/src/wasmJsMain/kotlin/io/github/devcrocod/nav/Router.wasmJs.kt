@file:OptIn(ExperimentalWasmJsInterop::class)

package io.github.devcrocod.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.events.Event

private val basePath: String = run {
    val href = document.querySelector("base")?.getAttribute("href").orEmpty()
    val origin = window.location.origin
    val pathPart = when {
        href.isEmpty() -> ""
        href.startsWith(origin) -> href.removePrefix(origin)
        href.startsWith("/") -> href
        else -> ""
    }
    pathPart.trimEnd('/')
}

private fun stripBase(pathname: String): String {
    if (basePath.isEmpty()) return pathname
    return when {
        pathname == basePath -> "/"
        pathname.startsWith("$basePath/") -> pathname.removePrefix(basePath)
        else -> pathname
    }
}

private fun withBase(path: String): String {
    if (basePath.isEmpty()) return path
    return if (path == "/") "$basePath/" else "$basePath$path"
}

@Composable
actual fun rememberRouter(): RouterState {
    val routeState = remember { mutableStateOf(parsePath(stripBase(window.location.pathname))) }

    DisposableEffect(Unit) {
        val listener: (Event) -> Unit = {
            val parsed = parsePath(stripBase(window.location.pathname))
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
                    val newPath = withBase(next.path)
                    if (window.location.pathname != newPath) {
                        window.history.pushState(null, "", newPath)
                    }
                }
            },
            goBack = { window.history.back() },
        )
    }
}
