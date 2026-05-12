package io.github.devcrocod.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import io.github.devcrocod.components.*
import io.github.devcrocod.data.findProject
import io.github.devcrocod.icons.PortfolioIcons
import io.github.devcrocod.platform.openExternalUrl
import io.github.devcrocod.theme.*

@Composable
fun ProjectDetailPage(
    projectId: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val ws = windowSize
    val pagePadding = when (ws) {
        WindowSize.Compact -> PaddingValues(top = 32.dp, bottom = 64.dp)
        WindowSize.Medium -> PaddingValues(top = 40.dp, bottom = 96.dp)
        WindowSize.Expanded -> PaddingValues(top = 48.dp, bottom = 128.dp)
    }
    val project = findProject(projectId)
    if (project == null) {
        PageContainer(modifier = modifier.padding(pagePadding)) {
            IconButton(
                icon = PortfolioIcons.ArrowLeft,
                onClick = onBack,
                contentDescription = "Back",
            )
            Spacer(Modifier.height(40.dp))
            Text(
                text = "Project not found",
                style = type.sectionH2,
                color = tokens.fg1,
            )
        }
        return
    }

    val plexMono = plexMonoFamily()
    val metaLabelStyle = TextStyle(
        fontFamily = plexMono,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.05.em,
    )
    val metaValueStyle = TextStyle(
        fontFamily = plexMono,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = (14 * 1.4f).sp,
        letterSpacing = 0.05.em,
    )
    val titleGap = ws.pick(40.dp, 48.dp, 56.dp)
    val eyebrowGap = ws.pick(12.dp, 14.dp, 16.dp)
    val metaGap = ws.pick(32.dp, 40.dp, 48.dp)
    val tagsGap = ws.pick(24.dp, 32.dp, 40.dp)
    val heroGap = ws.pick(48.dp, 60.dp, 72.dp)
    val overviewTopGap = ws.pick(64.dp, 80.dp, 96.dp)
    val codeGap = ws.pick(28.dp, 32.dp, 40.dp)
    val ctaGap = ws.pick(40.dp, 48.dp, 56.dp)

    PageContainer(modifier = modifier.padding(pagePadding)) {
        IconButton(
            icon = PortfolioIcons.ArrowLeft,
            onClick = onBack,
            contentDescription = "Back",
        )

        Spacer(Modifier.height(titleGap))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = project.year,
                style = type.eyebrow,
                color = tokens.fg3,
            )
            if (!project.stars.isNullOrBlank()) {
                Text(
                    text = "·",
                    style = type.eyebrow,
                    color = tokens.fg3,
                )
                Icon(
                    painter = PortfolioIcons.Star,
                    contentDescription = null,
                    tint = tokens.fg3,
                    modifier = Modifier.size(14.dp),
                )
                Text(
                    text = project.stars.uppercase(),
                    style = type.eyebrow,
                    color = tokens.fg3,
                )
            }
        }

        Spacer(Modifier.height(eyebrowGap))

        Text(text = project.title, style = type.projectTitle, color = tokens.fg1)

        Spacer(Modifier.height(metaGap))

        Grid(modifier = Modifier.fillMaxWidth()) {
            MetaItem(
                label = "Project",
                labelStyle = metaLabelStyle,
                modifier = Modifier.span(compact = 4, medium = 4, expanded = 2),
            ) {
                Text(
                    text = project.kind.uppercase(),
                    style = metaValueStyle,
                    color = tokens.fg1,
                )
            }
            val role = project.role
            if (role != null) {
                MetaItem(
                    label = "Role",
                    labelStyle = metaLabelStyle,
                    modifier = Modifier.span(compact = 4, medium = 4, expanded = 2),
                ) {
                    Text(
                        text = role.display.uppercase(),
                        style = metaValueStyle,
                        color = tokens.fg1,
                    )
                }
            } else {
                Box(modifier = Modifier.span(0, 0, 2))
            }
            MetaItem(
                label = "Link",
                labelStyle = metaLabelStyle,
                modifier = Modifier.span(compact = 4, medium = 4, expanded = 4),
            ) {
                GradientUnderlineLink(
                    text = "${project.repo} ↗".uppercase(),
                    onClick = { openExternalUrl(project.href) },
                    style = metaValueStyle,
                    color = tokens.fg1,
                    baseUnderlineColor = tokens.fg1,
                    maxLines = 1,
                    softWrap = false,
                )
            }
            Box(modifier = Modifier.span(0, 0, 4))
        }

        Spacer(Modifier.height(tagsGap))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            for (t in project.tags) Tag(text = t, size = TagSize.Md)
        }

        Spacer(Modifier.height(heroGap))

        HeroPlaceholder(
            project = project,
            modifier = Modifier.fillMaxWidth(),
            overlay = if (project.id == "koog") {
                { KoogAgentDiagram(accent = project.accent, modifier = Modifier.matchParentSize()) }
            } else null,
        )

        Spacer(Modifier.height(overviewTopGap))

        Grid(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.span(0, 0, 2))
            Column(modifier = Modifier.span(compact = 4, medium = 8, expanded = 8)) {
                Text(text = "Overview", style = type.sectionH3, color = tokens.fg1)
                Spacer(Modifier.height(if (ws.isCompact) 16.dp else 24.dp))
                Text(text = project.overview ?: project.desc, style = type.proseBodyLg, color = tokens.fg1)
                Spacer(Modifier.height(codeGap))
                CodeBlock(filename = "${project.id}.kt", code = project.snippet)
                Spacer(Modifier.height(ctaGap))
                PillLink(
                    text = "Check out ${project.title}",
                    onClick = { openExternalUrl(project.href) },
                    size = PillSize.Lg,
                    trailing = {
                        Text(
                            text = "↗",
                            style = type.pillLabel,
                            color = tokens.fg2,
                        )
                    },
                )
            }
            Box(modifier = Modifier.span(0, 0, 2))
        }
    }
}
