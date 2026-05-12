package io.github.devcrocod

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.chrisbanes.haze.rememberHazeState
import io.github.devcrocod.chrome.*
import io.github.devcrocod.nav.PortfolioRoutes
import io.github.devcrocod.nav.rememberRouter
import io.github.devcrocod.pages.HomePage
import io.github.devcrocod.pages.InfoPage
import io.github.devcrocod.pages.ProjectDetailPage
import io.github.devcrocod.pages.WorkPage
import io.github.devcrocod.theme.*

@Composable
fun App() {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val sizeClass = maxWidth.toWindowSize()
        CompositionLocalProvider(LocalWindowSize provides sizeClass) {
            var darkTheme by remember { mutableStateOf(true) }
            var drawerOpen by remember { mutableStateOf(false) }

            LaunchedEffect(sizeClass) {
                if (!sizeClass.isCompact) drawerOpen = false
            }

            PortfolioTheme(darkTheme = darkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = tokens.paper,
                ) {
                    val router = rememberRouter()
                    val hazeState = rememberHazeState()

                    LaunchedEffect(router.current) { drawerOpen = false }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = tokens.paper),
                    ) {
                        SelectionContainer {
                            PortfolioRoutes(
                                router = router,
                                home = { HomePage(onNavigate = it.navigate) },
                                work = { WorkPage(onNavigate = it.navigate) },
                                info = { InfoPage() },
                                project = { r, id -> ProjectDetailPage(projectId = id, onBack = r.goBack) },
                                footer = { Footer(onNavigate = router.navigate) },
                                modifier = Modifier.fillMaxSize(),
                                contentTopPadding = NavHeight,
                                hazeState = hazeState,
                            )
                        }
                        NavBackdrop(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopCenter),
                            hazeState = hazeState,
                        )
                        Nav(
                            currentRoute = router.current,
                            onNavigate = router.navigate,
                            isDark = darkTheme,
                            onToggleDark = { darkTheme = !darkTheme },
                            onMenuClick = { drawerOpen = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopCenter),
                        )
                        MobileNavDrawer(
                            open = drawerOpen,
                            currentRoute = router.current,
                            onNavigate = router.navigate,
                            onDismiss = { drawerOpen = false },
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
        }
    }
}
