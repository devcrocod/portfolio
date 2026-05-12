package io.github.devcrocod.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import io.github.devcrocod.platform.copyToClipboard
import io.github.devcrocod.theme.*
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun CodeBlock(
    filename: String?,
    code: String,
    modifier: Modifier = Modifier,
) {
    var copied by remember { mutableStateOf(false) }
    val isDark = tokens.isDark
    val annotated = remember(code, isDark) {
        kotlinTokens(code, if (isDark) CodeDarkPalette else CodeLightPalette)
    }
    val bg = if (isDark) CodeDarkPalette.bg else CodeLightPalette.bg
    val fg = if (isDark) CodeDarkPalette.fg else CodeLightPalette.fg
    val scrollNarrow = !windowSize.isExpanded

    LaunchedEffect(copied) {
        if (copied) {
            delay(1200.milliseconds)
            copied = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = bg)
            .border(width = 1.dp, color = tokens.border),
    ) {
        if (filename != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = tokens.paper)
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DisableSelection {
                    Text(text = filename, style = type.codeFilename, color = tokens.fg3)
                }
                Box(
                    modifier = Modifier
                        .border(width = 1.dp, color = tokens.border, shape = RoundedCornerShape(4.dp))
                        .background(color = tokens.paper, shape = RoundedCornerShape(4.dp))
                        .clickable {
                            copyToClipboard(code)
                            copied = true
                        }
                        .pointerHoverIcon(PointerIcon.Hand)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                ) {
                    DisableSelection {
                        Text(
                            text = if (copied) "Copied" else "Copy",
                            style = type.codeFilename.copy(fontSize = TextUnit(11f, TextUnitType.Sp)),
                            color = tokens.fg2,
                        )
                    }
                }
            }
            HorizontalDivider(thickness = 1.dp, color = tokens.border)
        }
        SelectionContainer {
            val scrollState = rememberScrollState()
            val codeBox: @Composable () -> Unit = {
                Text(
                    text = annotated,
                    style = type.codeBlock,
                    color = fg,
                    softWrap = !scrollNarrow,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                )
            }
            if (scrollNarrow) {
                Box(modifier = Modifier.fillMaxWidth().horizontalScroll(scrollState)) {
                    codeBox()
                }
            } else {
                Box(modifier = Modifier.fillMaxWidth()) {
                    codeBox()
                }
            }
        }
    }
}
