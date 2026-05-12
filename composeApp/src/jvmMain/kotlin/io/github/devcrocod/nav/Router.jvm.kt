package io.github.devcrocod.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
actual fun rememberRouter(): RouterState {
    val routeState = remember { mutableStateOf<Route>(Route.Home) }
    val history = remember { ArrayDeque<Route>() }
    return remember(routeState) {
        RouterState(
            routeState = routeState,
            navigate = { next ->
                if (routeState.value != next) {
                    history.addLast(routeState.value)
                    routeState.value = next
                }
            },
            goBack = {
                val prev = history.removeLastOrNull()
                if (prev != null) routeState.value = prev
            },
        )
    }
}
