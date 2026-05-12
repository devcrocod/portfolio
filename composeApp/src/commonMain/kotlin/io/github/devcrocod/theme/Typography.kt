package io.github.devcrocod.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import portfolio.composeapp.generated.resources.*

@Composable
fun plexSansFamily(): FontFamily {
    val regular = Font(Res.font.ibm_plex_sans_regular, FontWeight.Normal, FontStyle.Normal)
    val bold = Font(Res.font.ibm_plex_sans_bold, FontWeight.Bold, FontStyle.Normal)
    return remember(regular, bold) { FontFamily(regular, bold) }
}

@Composable
fun plexMonoFamily(): FontFamily {
    val regular = Font(Res.font.ibm_plex_mono_regular, FontWeight.Normal, FontStyle.Normal)
    return remember(regular) { FontFamily(regular) }
}

@Composable
fun monoFamily(): FontFamily {
    val regular = Font(Res.font.jetbrains_mono_regular, FontWeight.Normal, FontStyle.Normal)
    return remember(regular) { FontFamily(regular) }
}

fun portfolioM3Typography(sans: FontFamily, ws: WindowSize): Typography {
    fun ts(size: Int, weight: FontWeight = FontWeight.Normal, lhMul: Float = 1.5f, tracking: Float = 0f) =
        TextStyle(
            fontFamily = sans,
            fontWeight = weight,
            fontSize = size.sp,
            lineHeight = (size * lhMul).sp,
            letterSpacing = tracking.em,
        )

    val displayLargeSize = ws.pick(44, 64, 88)
    val displayMediumSize = ws.pick(32, 40, 48)
    val displaySmallSize = ws.pick(28, 34, 40)
    val headlineLargeSize = ws.pick(28, 34, 40)
    val headlineMediumSize = ws.pick(22, 26, 32)
    return Typography(
        displayLarge = ts(displayLargeSize, FontWeight.Bold, 1.05f, -0.03f),
        displayMedium = ts(displayMediumSize, FontWeight.Bold, 1.05f, -0.02f),
        displaySmall = ts(displaySmallSize, FontWeight.Bold, 1.1f, -0.02f),
        headlineLarge = ts(headlineLargeSize, FontWeight.Bold, 1.1f, -0.02f),
        headlineMedium = ts(headlineMediumSize, FontWeight.Bold, 1.1f, -0.02f),
        headlineSmall = ts(24, FontWeight.Bold, 1.2f, -0.02f),
        titleLarge = ts(24, FontWeight.Bold, 1.2f, -0.02f),
        titleMedium = ts(20, FontWeight.Bold, 1.2f, -0.02f),
        titleSmall = ts(16, FontWeight.Bold, 1.3f),
        bodyLarge = ts(20, FontWeight.Normal, 1.5f),
        bodyMedium = ts(16, FontWeight.Normal, 1.5f),
        bodySmall = ts(13, FontWeight.Normal, 1.5f),
        labelLarge = ts(13, FontWeight.Normal, 1.0f),
        labelMedium = ts(12, FontWeight.Normal, 1.0f),
        labelSmall = ts(12, FontWeight.Normal, 1.0f),
    )
}

@Immutable
data class PortfolioType(
    val eyebrow: TextStyle,
    val tag: TextStyle,
    val pillLabel: TextStyle,
    val navItem: TextStyle,
    val brandName: TextStyle,
    val cardKind: TextStyle,
    val cardFooter: TextStyle,
    val code: TextStyle,
    val codeSm: TextStyle,
    val codeBlock: TextStyle,
    val codeFilename: TextStyle,
    val footerMeta: TextStyle,
    val heroH1: TextStyle,
    val pageH1: TextStyle,
    val projectTitle: TextStyle,
    val cardTitleLg: TextStyle,
    val cardTitleMd: TextStyle,
    val featuredTitle: TextStyle,
    val sectionH2: TextStyle,
    val sectionH3: TextStyle,
    val proseLead: TextStyle,
    val proseBody: TextStyle,
    val proseBodyLg: TextStyle,
    val proseBodyMd: TextStyle,
    val terminal: TextStyle,
)

fun portfolioType(
    sans: FontFamily,
    plexMono: FontFamily,
    jbMono: FontFamily,
    ws: WindowSize,
): PortfolioType {
    val monoLabel = TextStyle(
        fontFamily = plexMono,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.05.em,
    )
    val pageH1Size = ws.pick(44, 64, 88)
    val sectionH2Size = ws.pick(30, 36, 44)
    val heroH1Size = ws.pick(28, 36, 44)
    val projectTitleSize = ws.pick(32, 40, 48)
    val sectionH3Size = ws.pick(22, 26, 32)
    val cardTitleLgSize = ws.pick(22, 24, 28)
    val cardTitleMdSize = ws.pick(18, 20, 22)
    val featuredTitleSize = ws.pick(22, 24, 28)
    val proseLeadSize = ws.pick(18, 22, 28)
    val proseLeadLh = 1.4f
    val proseBodyLgSize = ws.pick(17, 18, 20)
    val proseBodySize = ws.pick(15, 16, 16)
    val proseBodyMdSize = ws.pick(15, 16, 16)
    val navItemSize = ws.pick(15, 18, 18)
    val brandNameSize = ws.pick(14, 18, 18)
    val codeBlockSize = ws.pick(12, 13, 13)
    val terminalSize = ws.pick(12, 13, 13)
    return PortfolioType(
        eyebrow = monoLabel.copy(fontSize = 14.sp, lineHeight = 14.sp),
        tag = monoLabel.copy(fontSize = 12.sp, lineHeight = 12.sp),
        pillLabel = monoLabel.copy(fontSize = 12.sp, lineHeight = 12.sp),
        navItem = monoLabel.copy(fontSize = navItemSize.sp, lineHeight = navItemSize.sp),
        brandName = monoLabel.copy(fontSize = brandNameSize.sp, lineHeight = brandNameSize.sp),
        cardKind = monoLabel.copy(fontSize = 12.sp, lineHeight = 12.sp),
        cardFooter = monoLabel.copy(fontSize = 12.sp, lineHeight = (12 * 1.4f).sp),
        code = TextStyle(
            fontFamily = jbMono,
            fontSize = 13.sp,
            lineHeight = (13 * 1.55f).sp,
        ),
        codeSm = TextStyle(
            fontFamily = jbMono,
            fontSize = 12.sp,
            lineHeight = (12 * 1.55f).sp,
        ),
        codeBlock = TextStyle(
            fontFamily = jbMono,
            fontSize = codeBlockSize.sp,
            lineHeight = (codeBlockSize * 1.6f).sp,
        ),
        codeFilename = monoLabel.copy(fontSize = 12.sp, lineHeight = 12.sp),
        footerMeta = monoLabel.copy(fontSize = 12.sp, lineHeight = (12 * 1.4f).sp),
        heroH1 = TextStyle(
            fontFamily = sans,
            fontWeight = FontWeight.Normal,
            fontSize = heroH1Size.sp,
            lineHeight = (heroH1Size * 1.3f).sp,
            letterSpacing = (-0.01f).em,
        ),
        pageH1 = TextStyle(
            fontFamily = sans,
            fontWeight = FontWeight.Bold,
            fontSize = pageH1Size.sp,
            lineHeight = (pageH1Size * 1.05f).sp,
            letterSpacing = (-0.03f).em,
        ),
        projectTitle = TextStyle(
            fontFamily = sans,
            fontWeight = FontWeight.Bold,
            fontSize = projectTitleSize.sp,
            lineHeight = (projectTitleSize * 1.05f).sp,
            letterSpacing = (-0.02f).em,
        ),
        cardTitleLg = TextStyle(
            fontFamily = sans,
            fontWeight = FontWeight.Normal,
            fontSize = cardTitleLgSize.sp,
            lineHeight = (cardTitleLgSize * 1.3f).sp,
            letterSpacing = (-0.01f).em,
        ),
        cardTitleMd = TextStyle(
            fontFamily = sans,
            fontWeight = FontWeight.Normal,
            fontSize = cardTitleMdSize.sp,
            lineHeight = (cardTitleMdSize * 1.3f).sp,
            letterSpacing = (-0.01f).em,
        ),
        featuredTitle = TextStyle(
            fontFamily = sans,
            fontWeight = FontWeight.Normal,
            fontSize = featuredTitleSize.sp,
            lineHeight = (featuredTitleSize * 1.3f).sp,
            letterSpacing = (-0.01f).em,
        ),
        sectionH2 = TextStyle(
            fontFamily = sans,
            fontWeight = FontWeight.Bold,
            fontSize = sectionH2Size.sp,
            lineHeight = (sectionH2Size * 1.1f).sp,
            letterSpacing = (-0.02f).em,
        ),
        sectionH3 = TextStyle(
            fontFamily = sans,
            fontWeight = FontWeight.Bold,
            fontSize = sectionH3Size.sp,
            lineHeight = (sectionH3Size * 1.1f).sp,
            letterSpacing = (-0.02f).em,
        ),
        proseLead = TextStyle(
            fontFamily = sans,
            fontWeight = FontWeight.Normal,
            fontSize = proseLeadSize.sp,
            lineHeight = (proseLeadSize * proseLeadLh).sp,
            letterSpacing = (-0.005f).em,
        ),
        proseBody = TextStyle(
            fontFamily = sans,
            fontWeight = FontWeight.Normal,
            fontSize = proseBodySize.sp,
            lineHeight = (proseBodySize * 1.5f).sp,
        ),
        proseBodyLg = TextStyle(
            fontFamily = sans,
            fontWeight = FontWeight.Normal,
            fontSize = proseBodyLgSize.sp,
            lineHeight = (proseBodyLgSize * 1.5f).sp,
        ),
        proseBodyMd = TextStyle(
            fontFamily = sans,
            fontWeight = FontWeight.Normal,
            fontSize = proseBodyMdSize.sp,
            lineHeight = (proseBodyMdSize * 1.5f).sp,
        ),
        terminal = TextStyle(
            fontFamily = jbMono,
            fontSize = terminalSize.sp,
            lineHeight = (terminalSize * 1.5f).sp,
            textAlign = TextAlign.Start,
        ),
    )
}

val LocalPortfolioType = staticCompositionLocalOf<PortfolioType> {
    error("PortfolioTheme not provided")
}
