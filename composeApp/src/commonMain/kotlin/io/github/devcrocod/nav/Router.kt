package io.github.devcrocod.nav

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import io.github.devcrocod.theme.DurPage
import io.github.devcrocod.theme.PortfolioEasing

@Composable
expect fun rememberRouter(): RouterState

class RouterState(
    private val routeState: MutableState<Route>,
    val navigate: (Route) -> Unit,
    val goBack: () -> Unit,
) {
    val current: Route get() = routeState.value
}

@Composable
fun PortfolioRoutes(
    router: RouterState,
    home: @Composable (RouterState) -> Unit,
    work: @Composable (RouterState) -> Unit,
    info: @Composable (RouterState) -> Unit,
    project: @Composable (RouterState, String) -> Unit,
    footer: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    contentTopPadding: Dp = 0.dp,
    hazeState: HazeState? = null,
) {
    val rootModifier = modifier
        .let { if (hazeState != null) it.hazeSource(hazeState) else it }
    AnimatedContent(
        targetState = router.current,
        modifier = rootModifier,
        transitionSpec = {
            fadeIn(animationSpec = tween(DurPage, easing = PortfolioEasing)) togetherWith
                    fadeOut(animationSpec = tween(DurPage, easing = PortfolioEasing))
        },
        label = "page",
    ) { route ->
        val scroll = rememberScrollState()
        LaunchedEffect(route) { scroll.scrollTo(0) }
        Column(modifier = Modifier.fillMaxSize().verticalScroll(scroll)) {
            if (contentTopPadding > 0.dp) {
                Spacer(Modifier.height(contentTopPadding))
            }
            when (route) {
                is Route.Home -> home(router)
                is Route.Work -> work(router)
                is Route.Info -> info(router)
                is Route.ProjectDetail -> project(router, route.id)
            }
            footer()
        }
    }
}
