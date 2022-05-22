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
    primary = Color(0xFF1AAF41),
    onPrimary = Color.White,
    primaryVariant = Color(0xFF1AAF41),
    secondary = Color(0xFF69CB3A),
    secondaryVariant = Color(0xFF69CB3A),
    surface = Color.White,
    onSurface = Color.Black,
    background = Color.White
)

@SuppressLint("ConflictingOnColor")
internal val darkColors = darkColors(
    primary = Color(0xFF1AAF41),
    onPrimary = Color.White,
    primaryVariant = Color(0xFF1AAF41),
    secondary = Color(0xFF69CB3A),
    secondaryVariant = Color(0xFF69CB3A),
    surface = Color(0xFF2C2E31),
    onSurface = Color.White,
    background = Color(0xFF17191d)
)