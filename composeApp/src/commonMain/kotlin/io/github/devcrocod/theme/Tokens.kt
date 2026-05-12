package io.github.devcrocod.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class PortfolioColors(
    val paper: Color,
    val ink: Color,
    val n50: Color,
    val n100: Color,
    val n200: Color,
    val n300: Color,
    val n400: Color,
    val n500: Color,
    val n600: Color,
    val n700: Color,
    val n800: Color,
    val bgSubtle: Color,
    val fg1: Color,
    val fg2: Color,
    val fg3: Color,
    val border: Color,
    val borderStrong: Color,
    val success: Color,
    val warning: Color,
    val danger: Color,
    val info: Color,
    val isDark: Boolean,
)

fun lightTokens() = PortfolioColors(
    paper = Color(0xFFFDFDFC),
    ink = Color(0xFF19191C),
    n50 = Color(0xFFF6F6F4),
    n100 = Color(0xFFEDEDEA),
    n200 = Color(0xFFDCDCD7),
    n300 = Color(0xFFBDBDB5),
    n400 = Color(0xFF8E8E85),
    n500 = Color(0xFF6B6B62),
    n600 = Color(0xFF4A4A43),
    n700 = Color(0xFF2E2E29),
    n800 = Color(0xFF1F1F1C),
    bgSubtle = Color(0xFFF6F6F4),
    fg1 = Color(0xFF19191C),
    fg2 = Color(0xFF4A4A43),
    fg3 = Color(0xFF8E8E85),
    border = Color(0xFFDCDCD7),
    borderStrong = Color(0xFF8E8E85),
    success = Color(0xFF2E8B57),
    warning = Color(0xFFC28A1F),
    danger = Color(0xFFC0463A),
    info = Color(0xFF3D6FB8),
    isDark = false,
)

fun darkTokens() = PortfolioColors(
    paper = Color(0xFF0E0E10),
    ink = Color(0xFFF4F4F2),
    n50 = Color(0xFF16161A),
    n100 = Color(0xFF1C1C20),
    n200 = Color(0xFF26262C),
    n300 = Color(0xFF3A3A42),
    n400 = Color(0xFF5C5C66),
    n500 = Color(0xFF8A8A92),
    n600 = Color(0xFFB0B0B6),
    n700 = Color(0xFFD2D2D6),
    n800 = Color(0xFFEAEAEC),
    bgSubtle = Color(0xFF16161A),
    fg1 = Color(0xFFF4F4F2),
    fg2 = Color(0xFFD2D2D6),
    fg3 = Color(0xFF8A8A92),
    border = Color(0xFF26262C),
    borderStrong = Color(0xFF5C5C66),
    success = Color(0xFF2E8B57),
    warning = Color(0xFFC28A1F),
    danger = Color(0xFFC0463A),
    info = Color(0xFF3D6FB8),
    isDark = true,
)

val LocalPortfolioTokens = staticCompositionLocalOf<PortfolioColors> {
    error("PortfolioTheme not provided")
}

interface CodePalette {
    val fg: Color
    val comment: Color
    val string: Color
    val number: Color
    val keyword: Color
    val type: Color
    val fn: Color
    val punct: Color
}

object CodeDarkPalette : CodePalette {
    val bg = Color(0xFF0E0E12)
    override val fg = Color(0xFFE4E4E0)
    override val comment = Color(0xFF6B6B70)
    override val string = Color(0xFFA8E0A0)
    override val number = Color(0xFFFFC66D)
    override val keyword = Color(0xFFC77DFF)
    override val type = Color(0xFF69C9FF)
    override val fn = Color(0xFF82AAFF)
    override val punct = Color(0xFF9A9AA0)
    val grid = Color(0x0AFFFFFF)
}

object CodeLightPalette : CodePalette {
    val bg = Color(0xFFF4F2EE)
    override val fg = Color(0xFF2A2A2E)
    override val comment = Color(0xFF9A9A92)
    override val string = Color(0xFF3A8A3A)
    override val number = Color(0xFFB5651D)
    override val keyword = Color(0xFF7F52FF)
    override val type = Color(0xFF0E62B5)
    override val fn = Color(0xFF3D6FB8)
    override val punct = Color(0xFF6E6E68)
    val grid = Color(0x0B000000)
    val accentBorder = Color(0x0F000000)
}
