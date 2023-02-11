package app.saboten.androidUi.styles

import android.annotation.SuppressLint
import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

val Colors.surfaceOver
    get() = onSurface.copy(alpha = if (isLight) 0.03f else 0.1f).compositeOver(background)

object SabotenColors {

    val green200 = Color(0xFFAAE6C9)
    val green500 = Color(0xFF00C064)
    val selectedGreen = Color(0xFF087E46)
    val grey100 = Color(0xFFECECEC)
    val grey200 = Color(0xFFC6C6C6)
    val grey1000 = Color(0xFF111111)

}

@SuppressLint("ConflictingOnColor")
internal val lightColors = lightColors(
    primary = SabotenColors.green500,
    onPrimary = Color.White,
    primaryVariant = SabotenColors.green500,
    secondary = SabotenColors.green500,
    secondaryVariant = SabotenColors.green500,
    onSecondary = Color.White,
    surface = Color.White,
    onSurface = Color.Black,
    background = Color(0xFFF8F8F8)
)

@SuppressLint("ConflictingOnColor")
internal val darkColors = darkColors(
    primary = SabotenColors.green500,
    onPrimary = Color.White,
    primaryVariant = SabotenColors.green500,
    secondary = SabotenColors.green500,
    secondaryVariant = SabotenColors.green500,
    surface = Color(0xFF191C20),
    onSurface = Color.White,
    background = Color(0xFF0F1013)
)
