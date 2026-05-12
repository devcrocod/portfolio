package io.github.devcrocod.nav

sealed interface Route {
    val path: String

    data object Home : Route {
        override val path: String = "/"
    }

    data object Work : Route {
        override val path: String = "/work"
    }

    data object Info : Route {
        override val path: String = "/info"
    }

    data class ProjectDetail(val id: String) : Route {
        override val path: String = "/project/$id"
    }
}

fun parsePath(rawPath: String): Route {
    val path = rawPath.trim().let { it.ifEmpty { "/" } }
    return when {
        path == "/" -> Route.Home
        path == "/work" -> Route.Work
        path == "/info" -> Route.Info
        path.startsWith("/project/") -> {
            val id = path.removePrefix("/project/").trimEnd('/')
            if (id.isNotEmpty()) Route.ProjectDetail(id) else Route.Home
        }

        else -> Route.Home
    }
}
