package app.saboten.androidUi.styles

import android.annotation.SuppressLint
import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

val Colors.surfaceOver
    get() = onSurface.copy(alpha = if (isLight) 0.03f else 0.1f).compositeOver(background)

@SuppressLint("ConflictingOnColor")
internal val lightColors = lightColors(
    primary = Color(0xFF2EC781),
    onPrimary = Color.White,
    primaryVariant = Color(0xFF22A76A),
    secondary = Color(0xFF2EC781),
    secondaryVariant = Color(0xFF22A76A),
    surface = Color.White,
    onSurface = Color.Black,
    background = Color(0xFFF8F8F8)
)

@SuppressLint("ConflictingOnColor")
internal val darkColors = darkColors(
    primary = Color(0xFF17191D),
    onPrimary = Color.White,
    primaryVariant = Color(0xFF17191D),
    secondary = Color(0xFF22A76A),
    secondaryVariant = Color(0xFF156A43),
    surface = Color(0xFF191919),
    onSurface = Color.White,
    background = Color(0xFF0F0F0F)
)