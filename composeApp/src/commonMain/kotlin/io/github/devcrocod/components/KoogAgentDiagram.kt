package io.github.devcrocod.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.devcrocod.theme.plexMonoFamily
import io.github.devcrocod.theme.tokens
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

private enum class NodeShape { Rect, Hex }

private class DNode(
    val id: String,
    val label: String,
    val cx: Float,
    val cy: Float,
    val shape: NodeShape,
)

private enum class EdgeKind { Forward, BackLeft, BackRight }

private class DEdge(
    val from: String,
    val to: String,
    val label: String,
    val kind: EdgeKind = EdgeKind.Forward,
)

private val NODES = listOf(
    DNode("input", "Input", 0.50f, 0.10f, NodeShape.Rect),
    DNode("llm", "Request LLM", 0.50f, 0.28f, NodeShape.Rect),
    DNode("toolCall", "onToolCall", 0.27f, 0.50f, NodeShape.Hex),
    DNode("assist", "onAssistantMessage", 0.73f, 0.50f, NodeShape.Hex),
    DNode("exec", "Execute tool call", 0.27f, 0.72f, NodeShape.Rect),
    DNode("output", "Output", 0.73f, 0.72f, NodeShape.Rect),
    DNode("send", "Request LLM", 0.27f, 0.92f, NodeShape.Rect),
)

private val NODES_BY_ID = NODES.associateBy { it.id }

private val EDGES = listOf(
    DEdge("input", "llm", "String"),
    DEdge("llm", "toolCall", "Message.Response"),
    DEdge("llm", "assist", "Message.Response"),
    DEdge("assist", "output", "String"),
    DEdge("toolCall", "exec", "Message.Tool.Call"),
    DEdge("exec", "send", "ReceivedToolResult"),
    DEdge("send", "toolCall", "Message.Response", EdgeKind.BackLeft),
    DEdge("send", "assist", "Message.Response", EdgeKind.BackRight),
)

@Composable
fun KoogAgentDiagram(
    accent: Color,
    modifier: Modifier = Modifier,
) {
    val palette = tokens
    val mono = plexMonoFamily()
    val measurer = rememberTextMeasurer()
    val nodeStyle = remember(mono, palette.fg1) {
        TextStyle(
            fontFamily = mono,
            fontSize = 11.sp,
            lineHeight = 13.sp,
            color = palette.fg1,
            textAlign = TextAlign.Center,
        )
    }
    val edgeLabelStyle = remember(mono, palette.fg3) {
        TextStyle(
            fontFamily = mono,
            fontSize = 9.sp,
            lineHeight = 10.sp,
            color = palette.fg3,
            textAlign = TextAlign.Center,
        )
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val nodeW = w * 0.20f
        val nodeH = (h * 0.10f).coerceAtLeast(26.dp.toPx())
        val cornerR = 6.dp.toPx()
        val strokeW = 1.2.dp.toPx()
        val borderW = 1.dp.toPx()
        val arrowSize = 7.dp.toPx()

        val nodeBg = palette.paper
        val nodeBorder = accent.copy(alpha = if (palette.isDark) 0.55f else 0.40f)
        val edgeColor = palette.fg3.copy(alpha = 0.7f)
        val backEdgeColor = accent.copy(alpha = if (palette.isDark) 0.75f else 0.65f)
        val labelBg = palette.bgSubtle

        fun rectOf(node: DNode): Rect {
            val cx = node.cx * w
            val cy = node.cy * h
            return Rect(cx - nodeW / 2f, cy - nodeH / 2f, cx + nodeW / 2f, cy + nodeH / 2f)
        }

        for (edge in EDGES) {
            val from = NODES_BY_ID.getValue(edge.from)
            val to = NODES_BY_ID.getValue(edge.to)
            val fromRect = rectOf(from)
            val toRect = rectOf(to)
            val color = if (edge.kind == EdgeKind.Forward) edgeColor else backEdgeColor
            when (edge.kind) {
                EdgeKind.Forward -> drawForwardEdge(
                    measurer, edge.label, fromRect, toRect,
                    color, strokeW, arrowSize, edgeLabelStyle, labelBg,
                )
                EdgeKind.BackLeft -> drawBackEdgeLeft(
                    measurer, edge.label, fromRect, toRect,
                    color, strokeW, arrowSize, edgeLabelStyle, labelBg,
                )
                EdgeKind.BackRight -> drawBackEdgeRight(
                    measurer, edge.label, fromRect, toRect,
                    color, strokeW, arrowSize, edgeLabelStyle, labelBg,
                )
            }
        }

        for (node in NODES) {
            drawDiagNode(
                measurer = measurer,
                node = node,
                rect = rectOf(node),
                style = nodeStyle,
                bg = nodeBg,
                border = nodeBorder,
                borderWidth = borderW,
                cornerRadius = cornerR,
            )
        }
    }
}

private fun DrawScope.drawForwardEdge(
    measurer: TextMeasurer,
    label: String,
    fromRect: Rect,
    toRect: Rect,
    color: Color,
    strokeWidth: Float,
    arrowSize: Float,
    labelStyle: TextStyle,
    labelBg: Color,
) {
    val start = rectExit(fromRect, toRect.center)
    val end = rectExit(toRect, fromRect.center)
    drawLine(color = color, start = start, end = end, strokeWidth = strokeWidth)
    val angle = atan2(end.y - start.y, end.x - start.x)
    drawArrowhead(end, angle, color, arrowSize)
    val mid = Offset((start.x + end.x) / 2f, (start.y + end.y) / 2f)
    drawEdgeLabel(measurer, label, mid, labelStyle, labelBg)
}

private fun DrawScope.drawBackEdgeLeft(
    measurer: TextMeasurer,
    label: String,
    fromRect: Rect,
    toRect: Rect,
    color: Color,
    strokeWidth: Float,
    arrowSize: Float,
    labelStyle: TextStyle,
    labelBg: Color,
) {
    val start = Offset(fromRect.left, fromRect.center.y)
    val end = Offset(toRect.left, toRect.center.y)
    val bulge = size.width * 0.09f
    val c1 = Offset(start.x - bulge, start.y)
    val c2 = Offset(end.x - bulge, end.y)
    val path = Path().apply {
        moveTo(start.x, start.y)
        cubicTo(c1.x, c1.y, c2.x, c2.y, end.x, end.y)
    }
    drawPath(path, color = color, style = Stroke(width = strokeWidth))
    val angle = atan2(end.y - c2.y, end.x - c2.x)
    drawArrowhead(end, angle, color, arrowSize)
    drawEdgeLabel(measurer, label, cubicAt(start, c1, c2, end, 0.5f), labelStyle, labelBg)
}

private fun DrawScope.drawBackEdgeRight(
    measurer: TextMeasurer,
    label: String,
    fromRect: Rect,
    toRect: Rect,
    color: Color,
    strokeWidth: Float,
    arrowSize: Float,
    labelStyle: TextStyle,
    labelBg: Color,
) {
    val start = Offset(fromRect.right, fromRect.center.y)
    val end = Offset(toRect.left, toRect.center.y)
    val bulgeOut = size.width * 0.10f
    val bulgeIn = size.width * 0.05f
    val c1 = Offset(start.x + bulgeOut, start.y)
    val c2 = Offset(end.x - bulgeIn, end.y)
    val path = Path().apply {
        moveTo(start.x, start.y)
        cubicTo(c1.x, c1.y, c2.x, c2.y, end.x, end.y)
    }
    drawPath(path, color = color, style = Stroke(width = strokeWidth))
    val angle = atan2(end.y - c2.y, end.x - c2.x)
    drawArrowhead(end, angle, color, arrowSize)
    drawEdgeLabel(measurer, label, cubicAt(start, c1, c2, end, 0.5f), labelStyle, labelBg)
}

private fun cubicAt(p0: Offset, p1: Offset, p2: Offset, p3: Offset, t: Float): Offset {
    val u = 1f - t
    val x = u * u * u * p0.x + 3 * u * u * t * p1.x + 3 * u * t * t * p2.x + t * t * t * p3.x
    val y = u * u * u * p0.y + 3 * u * u * t * p1.y + 3 * u * t * t * p2.y + t * t * t * p3.y
    return Offset(x, y)
}

private fun rectExit(rect: Rect, towards: Offset): Offset {
    val cx = rect.center.x
    val cy = rect.center.y
    val dx = towards.x - cx
    val dy = towards.y - cy
    if (dx == 0f && dy == 0f) return rect.center
    val halfW = rect.width / 2f
    val halfH = rect.height / 2f
    val tx = if (dx != 0f) halfW / abs(dx) else Float.POSITIVE_INFINITY
    val ty = if (dy != 0f) halfH / abs(dy) else Float.POSITIVE_INFINITY
    val t = min(tx, ty)
    return Offset(cx + dx * t, cy + dy * t)
}

private fun DrawScope.drawArrowhead(
    tip: Offset,
    angle: Float,
    color: Color,
    size: Float,
) {
    val back = angle + PI.toFloat()
    val spread = 0.45f
    val a1 = back - spread
    val a2 = back + spread
    val path = Path().apply {
        moveTo(tip.x, tip.y)
        lineTo(tip.x + cos(a1) * size, tip.y + sin(a1) * size)
        lineTo(tip.x + cos(a2) * size, tip.y + sin(a2) * size)
        close()
    }
    drawPath(path, color = color)
}

private fun DrawScope.drawDiagNode(
    measurer: TextMeasurer,
    node: DNode,
    rect: Rect,
    style: TextStyle,
    bg: Color,
    border: Color,
    borderWidth: Float,
    cornerRadius: Float,
) {
    when (node.shape) {
        NodeShape.Rect -> {
            drawRoundRect(
                color = bg,
                topLeft = Offset(rect.left, rect.top),
                size = Size(rect.width, rect.height),
                cornerRadius = CornerRadius(cornerRadius),
            )
            drawRoundRect(
                color = border,
                topLeft = Offset(rect.left, rect.top),
                size = Size(rect.width, rect.height),
                cornerRadius = CornerRadius(cornerRadius),
                style = Stroke(width = borderWidth),
            )
        }
        NodeShape.Hex -> {
            val path = hexPath(rect)
            drawPath(path, color = bg)
            drawPath(path, color = border, style = Stroke(width = borderWidth))
        }
    }
    val padPx = 8.dp.toPx()
    val maxTextW = (rect.width - padPx * 2f).coerceAtLeast(4f).toInt()
    val measured = measurer.measure(
        text = node.label,
        style = style,
        constraints = Constraints(maxWidth = maxTextW),
        maxLines = 2,
    )
    val tx = rect.left + (rect.width - measured.size.width) / 2f
    val ty = rect.top + (rect.height - measured.size.height) / 2f
    drawText(measured, topLeft = Offset(tx, ty))
}

private fun hexPath(rect: Rect): Path {
    val inset = rect.height * 0.42f
    return Path().apply {
        moveTo(rect.left + inset, rect.top)
        lineTo(rect.right - inset, rect.top)
        lineTo(rect.right, rect.center.y)
        lineTo(rect.right - inset, rect.bottom)
        lineTo(rect.left + inset, rect.bottom)
        lineTo(rect.left, rect.center.y)
        close()
    }
}

private fun DrawScope.drawEdgeLabel(
    measurer: TextMeasurer,
    text: String,
    center: Offset,
    style: TextStyle,
    bg: Color,
) {
    val measured = measurer.measure(text = text, style = style)
    val px = 5.dp.toPx()
    val py = 2.dp.toPx()
    val w = measured.size.width.toFloat()
    val h = measured.size.height.toFloat()
    val left = center.x - w / 2f
    val top = center.y - h / 2f
    drawRoundRect(
        color = bg,
        topLeft = Offset(left - px, top - py),
        size = Size(w + px * 2f, h + py * 2f),
        cornerRadius = CornerRadius(3.dp.toPx()),
    )
    drawText(measured, topLeft = Offset(left, top))
}
