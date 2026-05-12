package io.github.devcrocod.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.devcrocod.theme.pick
import io.github.devcrocod.theme.tokens
import io.github.devcrocod.theme.type
import io.github.devcrocod.theme.windowSize

@Composable
fun PageTitle(
    text: String,
    modifier: Modifier = Modifier,
    count: Int? = null,
) {
    val ws = windowSize
    val countSize = ws.pick(20, 24, 28)
    val countTopPad = ws.pick(8, 12, 18)
    Row(modifier = modifier, verticalAlignment = Alignment.Top) {
        Text(
            text = text,
            style = type.pageH1,
            color = tokens.fg1,
        )
        if (count != null) {
            Spacer(Modifier.width(8.dp))
            Text(
                text = count.toString(),
                fontSize = countSize.sp,
                fontWeight = FontWeight.Medium,
                color = tokens.fg2,
                modifier = Modifier.padding(top = countTopPad.dp),
            )
        }
    }
}
