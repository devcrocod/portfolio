package io.github.devcrocod.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import io.github.devcrocod.theme.Spacing
import io.github.devcrocod.theme.WindowSize
import io.github.devcrocod.theme.gridGap
import io.github.devcrocod.theme.windowSize
import kotlin.math.roundToInt

interface GridScope {
    fun Modifier.span(cols: Int): Modifier
    fun Modifier.span(compact: Int, medium: Int, expanded: Int): Modifier
    fun Modifier.spanFull(): Modifier
}

private data class GridSpanData(
    val compact: Int,
    val medium: Int,
    val expanded: Int,
    val authoredAgainst: Int,
)

private data class GridSpanModifier(val data: GridSpanData) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?): Any = data
}

private class GridScopeImpl : GridScope {
    override fun Modifier.span(cols: Int): Modifier =
        this.then(GridSpanModifier(GridSpanData(cols, cols, cols, Spacing.GridColumnsLarge)))

    override fun Modifier.span(compact: Int, medium: Int, expanded: Int): Modifier =
        this.then(GridSpanModifier(GridSpanData(compact, medium, expanded, -1)))

    override fun Modifier.spanFull(): Modifier =
        this.then(GridSpanModifier(GridSpanData(-1, -1, -1, -1)))
}

private fun resolveSpan(data: GridSpanData, ws: WindowSize, totalCols: Int): Int {
    val raw = when (ws) {
        WindowSize.Compact -> data.compact
        WindowSize.Medium -> data.medium
        WindowSize.Expanded -> data.expanded
    }
    if (raw < 0) return totalCols
    if (raw == 0) return 0
    val rescaled = if (data.authoredAgainst > 0 && data.authoredAgainst != totalCols) {
        (raw.toFloat() * totalCols / data.authoredAgainst).roundToInt().coerceAtLeast(1)
    } else {
        raw
    }
    return rescaled.coerceIn(1, totalCols)
}

@Composable
fun Grid(
    modifier: Modifier = Modifier,
    gap: Dp = gridGap(),
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    content: @Composable GridScope.() -> Unit,
) {
    val ws = windowSize
    val totalCols = when (ws) {
        WindowSize.Compact -> Spacing.GridColumnsCompact
        WindowSize.Medium -> Spacing.GridColumnsMedium
        WindowSize.Expanded -> Spacing.GridColumnsLarge
    }
    val scope = remember { GridScopeImpl() }
    Layout(
        modifier = modifier,
        content = { scope.content() },
    ) { measurables, constraints ->
        val gapPx = gap.roundToPx()
        val maxWidth = constraints.maxWidth
        val totalGapsWidth = (totalCols - 1) * gapPx
        val colWidth = ((maxWidth - totalGapsWidth) / totalCols).coerceAtLeast(0)

        val spans = measurables.map { m ->
            val data = m.parentData as? GridSpanData ?: GridSpanData(1, 1, 1, Spacing.GridColumnsLarge)
            resolveSpan(data, ws, totalCols)
        }

        val visibleIndices = spans.withIndex().filter { it.value > 0 }.map { it.index }

        val itemWidths = spans.map { span -> (span * colWidth + (span - 1).coerceAtLeast(0) * gapPx).coerceAtLeast(0) }

        val rowOfItem = IntArray(measurables.size) { -1 }
        val rows = mutableListOf<MutableList<Int>>()
        var currentRow = mutableListOf<Int>()
        var cumulativeCols = 0
        for (i in visibleIndices) {
            val span = spans[i]
            if (cumulativeCols + span > totalCols && currentRow.isNotEmpty()) {
                rows += currentRow
                currentRow = mutableListOf()
                cumulativeCols = 0
            }
            rowOfItem[i] = rows.size
            currentRow += i
            cumulativeCols += span
        }
        if (currentRow.isNotEmpty()) rows += currentRow

        val intrinsicHeights = measurables.mapIndexed { i, m ->
            if (spans[i] == 0) 0 else m.minIntrinsicHeight(itemWidths[i])
        }
        val rowHeights = rows.map { row -> row.maxOf { intrinsicHeights[it] } }

        val placeables = measurables.mapIndexed { i, m ->
            if (spans[i] == 0) {
                m.measure(Constraints.fixed(0, 0))
            } else {
                val rowH = rowHeights[rowOfItem[i]]
                m.measure(
                    Constraints(
                        minWidth = itemWidths[i],
                        maxWidth = itemWidths[i],
                        minHeight = rowH,
                        maxHeight = rowH,
                    ),
                )
            }
        }

        val totalHeight = rowHeights.sum() + (rows.size - 1).coerceAtLeast(0) * gapPx

        layout(maxWidth, totalHeight) {
            var y = 0
            rows.forEachIndexed { rowIdx, row ->
                var col = 0
                row.forEach { i ->
                    val span = spans[i]
                    val placeable = placeables[i]
                    val x = col * (colWidth + gapPx)
                    val yOffset = verticalAlignment.align(placeable.height, rowHeights[rowIdx])
                    placeable.place(x, y + yOffset)
                    col += span
                }
                y += rowHeights[rowIdx] + gapPx
            }
            // Zero-span children must still be placed to keep Compose's layout-tree state in sync.
            for (i in placeables.indices) {
                if (spans[i] == 0) placeables[i].place(0, 0)
            }
        }
    }
}

