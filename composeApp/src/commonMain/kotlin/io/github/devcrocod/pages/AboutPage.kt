package io.github.devcrocod.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.github.devcrocod.components.*
import io.github.devcrocod.icons.PortfolioIcons
import io.github.devcrocod.platform.openExternalUrl
import io.github.devcrocod.theme.*
import org.jetbrains.compose.resources.painterResource
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.portrait

private data class SkillGroup(val category: String, val items: List<String>)

private val SKILLS = listOf(
    SkillGroup("Core", listOf("Kotlin", "Coroutines", "Multiplatform")),
    SkillGroup("Tooling", listOf("Gradle", "Compiler plugins", "LLMs / agents")),
    SkillGroup("Targets", listOf("JVM", "Native", "JS")),
)

@Composable
fun AboutPage(modifier: Modifier = Modifier) {
    val ws = windowSize

    PageContainer(modifier = modifier.padding(pageVerticalPadding())) {
        PageTitle(text = "Info")

        Spacer(Modifier.height(sectionGap()))
        HorizontalDivider(color = tokens.fg1)
        Spacer(Modifier.height(blockGap()))

        if (ws.isCompact) {
            AboutHeroCompact()
        } else {
            AboutHeroRegular()
        }

        Spacer(Modifier.height(sectionGap()))

        Text(
            text = "Connect",
            style = type.sectionH2,
            color = tokens.fg1,
        )
        Spacer(Modifier.height(blockGap()))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PillLink(
                text = "github",
                onClick = { openExternalUrl("https://github.com/devcrocod") },
            )
            PillLink(
                text = "linkedin",
                onClick = { openExternalUrl("https://www.linkedin.com/in/pavelgorgulov/") },
            )
        }

        Spacer(Modifier.height(sectionGap()))

        Text(
            text = "Skills",
            style = type.sectionH2,
            color = tokens.fg1,
        )
        Spacer(Modifier.height(blockGap()))
        Grid(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
        ) {
            SKILLS.forEach { group ->
                Column(
                    modifier = Modifier.span(compact = 4, medium = 4, expanded = 2),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Eyebrow(text = group.category)
                        HorizontalDivider(color = tokens.border)
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        group.items.forEach { item ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                Text(
                                    text = "→",
                                    style = type.code,
                                    color = tokens.fg3,
                                )
                                Text(
                                    text = item,
                                    style = type.proseBodyLg,
                                    color = tokens.fg1,
                                )
                            }
                        }
                    }
                }
                Box(modifier = Modifier.span(0, 0, 1))
            }
        }
    }
}

@Composable
private fun AboutHeroCompact() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Portrait(maxWidthDp = null)
        Spacer(Modifier.height(40.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            AboutBodyText()
            Spacer(Modifier.height(blockGap()))
            DownloadResumePill()
        }
    }
}

@Composable
private fun AboutHeroRegular() {
    Grid(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Column(modifier = Modifier.span(compact = 4, medium = 8, expanded = 7)) {
            AboutBodyText()
            Spacer(Modifier.height(blockGap()))
            DownloadResumePill()
        }

        Box(modifier = Modifier.span(0, 0, 2))

        Box(
            modifier = Modifier.span(compact = 4, medium = 3, expanded = 3),
            contentAlignment = Alignment.TopCenter,
        ) {
            Portrait()
        }
    }
}

@Composable
private fun AboutBodyText() {
    Text(
        text = "I'm Pavel Gorgulov, a Kotlin developer @JetBrains with a math background from SPbU and a passion for building the tools other developers depend on. I'm currently working on Kotlin AI, writing the libraries, frameworks, and tooling that go into building AI-based applications. Previously, I worked on Kotlin for Data Science, helping shape libraries like Multik, Kandy, and DataFrame. On the side, I keep contributing to open-source — Oremif and a handful of personal projects.",
        style = type.proseLead,
        color = tokens.fg1,
    )
}

@Composable
private fun DownloadResumePill() {
    PillLink(
        text = "download resume",
        onClick = { /* TODO: wire resume PDF */ },
        size = PillSize.Lg,
        trailing = {
            Icon(
                painter = PortfolioIcons.Download,
                contentDescription = null,
                tint = tokens.fg2,
                modifier = Modifier.size(20.dp),
            )
        },
    )
}

@Composable
private fun Portrait(maxWidthDp: androidx.compose.ui.unit.Dp? = null) {
    Image(
        painter = painterResource(Res.drawable.portrait),
        contentDescription = "Portrait of Pavel Gorgulov",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .let { if (maxWidthDp != null) it.widthIn(max = maxWidthDp) else it.fillMaxWidth() }
            .aspectRatio(1f)
            .clip(CircleShape)
            .border(width = 1.dp, color = tokens.border, shape = CircleShape),
    )
}
