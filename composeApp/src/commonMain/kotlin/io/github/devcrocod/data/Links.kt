package io.github.devcrocod.data

import io.github.devcrocod.nav.Route

object SocialLinks {
    const val GitHub = "https://github.com/devcrocod"
    const val LinkedIn = "https://www.linkedin.com/in/pavelgorgulov/"
}

data class NavEntry(val label: String, val route: Route)

val PrimaryNav: List<NavEntry> = listOf(
    NavEntry("work", Route.Work),
    NavEntry("info", Route.Info),
)
