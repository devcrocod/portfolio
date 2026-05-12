package io.github.devcrocod.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import io.github.devcrocod.theme.tokens
import org.jetbrains.compose.resources.painterResource
import portfolio.composeapp.generated.resources.*

object PortfolioIcons {

    val Star: Painter
        @Composable get() = painterResource(Res.drawable.lucide_star)

    val Sun: Painter
        @Composable get() = painterResource(Res.drawable.lucide_sun)

    val Moon: Painter
        @Composable get() = painterResource(Res.drawable.lucide_moon)

    val Download: Painter
        @Composable get() = painterResource(Res.drawable.lucide_download)

    val ArrowLeft: Painter
        @Composable get() = painterResource(Res.drawable.lucide_arrow_left)

    val Menu: Painter
        @Composable get() = painterResource(Res.drawable.lucide_menu)

    val Close: Painter
        @Composable get() = painterResource(Res.drawable.lucide_x)

    val KotlinMarkMono: Painter
        @Composable get() = painterResource(
            if (tokens.isDark) Res.drawable.kotlin_icon_dark else Res.drawable.kotlin_icon_light,
        )

    val KotlinMarkPrimary: Painter
        @Composable get() = painterResource(Res.drawable.kotlin_icon_primary)

    val ComposeMultiplatformMark: Painter
        @Composable get() = painterResource(
            if (tokens.isDark) Res.drawable.compose_multiplatform_icon_dark else Res.drawable.compose_multiplatform_icon_light,
        )

    val ComposeMultiplatformMarkPrimary: Painter
        @Composable get() = painterResource(Res.drawable.compose_multiplatform_icon_primary)
}
