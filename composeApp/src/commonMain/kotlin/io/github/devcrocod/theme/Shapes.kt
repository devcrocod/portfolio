package io.github.devcrocod.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val ShapeButton = RoundedCornerShape(6.dp)
val ShapeCard = RoundedCornerShape(12.dp)
val ShapeCodeSurface = RoundedCornerShape(6.dp)
val ShapeInput = RoundedCornerShape(8.dp)
val ShapePill = RoundedCornerShape(999.dp)

val PortfolioShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = ShapeButton,
    medium = ShapeInput,
    large = ShapeCard,
    extraLarge = RoundedCornerShape(16.dp),
)
