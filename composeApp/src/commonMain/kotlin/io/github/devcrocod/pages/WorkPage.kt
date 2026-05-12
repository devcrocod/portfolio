package io.github.devcrocod.pages

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.devcrocod.components.*
import io.github.devcrocod.data.FEATURED
import io.github.devcrocod.data.OREMIF
import io.github.devcrocod.data.PETS
import io.github.devcrocod.data.PROJECTS
import io.github.devcrocod.nav.Route
import io.github.devcrocod.theme.blockGap
import io.github.devcrocod.theme.pageVerticalPadding
import io.github.devcrocod.theme.sectionGap
import io.github.devcrocod.theme.tokens

@Composable
fun WorkPage(
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    PageContainer(modifier = modifier.padding(pageVerticalPadding())) {
        PageTitle(text = "Work", count = PROJECTS.size)

        Spacer(Modifier.height(sectionGap()))
        HorizontalDivider(color = tokens.fg1)
        Spacer(Modifier.height(blockGap()))

        Eyebrow("featured")
        Spacer(Modifier.height(blockGap()))
        Grid(modifier = Modifier.fillMaxWidth()) {
            for (p in FEATURED) {
                CodeCard(
                    project = p,
                    modifier = Modifier.span(compact = 4, medium = 4, expanded = 6),
                    size = CardSize.Lg,
                    onClick = { onNavigate(Route.ProjectDetail(p.id)) },
                )
            }
        }

        Spacer(Modifier.height(sectionGap()))
        HorizontalDivider(color = tokens.fg1)
        Spacer(Modifier.height(blockGap()))

        Eyebrow("pets · oremif")
        Spacer(Modifier.height(blockGap()))
        Grid(modifier = Modifier.fillMaxWidth()) {
            for (p in OREMIF) {
                CodeCard(
                    project = p,
                    modifier = Modifier.span(compact = 4, medium = 4, expanded = 6),
                    size = CardSize.Lg,
                    onClick = { onNavigate(Route.ProjectDetail(p.id)) },
                )
            }
        }

        Spacer(Modifier.height(sectionGap()))
        HorizontalDivider(color = tokens.fg1)
        Spacer(Modifier.height(blockGap()))

        Eyebrow("pets · personal")
        Spacer(Modifier.height(blockGap()))
        Grid(modifier = Modifier.fillMaxWidth()) {
            for (p in PETS) {
                CodeCard(
                    project = p,
                    modifier = Modifier.span(compact = 4, medium = 4, expanded = 4),
                    size = CardSize.Md,
                    onClick = { onNavigate(Route.ProjectDetail(p.id)) },
                )
            }
        }
    }
}
