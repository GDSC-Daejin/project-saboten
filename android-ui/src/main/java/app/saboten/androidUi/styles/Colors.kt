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
    /*
    /* Ellipse 212 */

position: absolute;
width: 76px;
height: 75px;
left: 197px;
top: 1210px;

/* Sab_Grey/100 */
background: #ECECEC;


/* Ellipse 221 */

position: absolute;
width: 76px;
height: 75px;
left: 111px;
top: 1210px;

/* ffffff */
background: #FFFFFF;


/* Ellipse 213 */

position: absolute;
width: 76px;
height: 75px;
left: 283px;
top: 1210px;

/* Sab_Grey/200 */
background: #D9D9D9;


/* Ellipse 214 */

position: absolute;
width: 76px;
height: 75px;
left: 369px;
top: 1210px;

/* Sab_Grey/300 */
background: #C6C6C6;


/* Ellipse 215 */

position: absolute;
width: 76px;
height: 75px;
left: 455px;
top: 1210px;

/* Sab_Grey/400 */
background: #B3B3B3;


/* Ellipse 216 */

position: absolute;
width: 75px;
height: 75px;
left: 542px;
top: 1210px;

/* Sab_Grey/500 */
background: #A0A0A0;


/* Ellipse 217 */

position: absolute;
width: 76px;
height: 75px;
left: 628px;
top: 1210px;

/* Sab_Grey/600 */
background: #808080;


/* Ellipse 218 */

position: absolute;
width: 76px;
height: 75px;
left: 714px;
top: 1210px;

/* Sab_Grey/700 */
background: #606060;


/* Ellipse 219 */

position: absolute;
width: 76px;
height: 75px;
left: 800px;
top: 1210px;

/* Sab_Grey/800 */
background: #404040;


/* Ellipse 220 */

position: absolute;
width: 76px;
height: 75px;
left: 886px;
top: 1210px;

/* Sab_Grey/900 */
background: #202020;


/* Ellipse 222 */

position: absolute;
width: 76px;
height: 75px;
left: 972px;
top: 1210px;

/* Sab_Grey/1000 */
background: #111111;

    * */

    val green100 = Color(0xFFE7F7EF)
    val green200 = Color(0xFFAAE6C9)
    val green300 = Color(0xFF79D3A8)
    val green400 = Color(0xFF2ECB7F)
    val green500 = Color(0xFF00C064)
    val green600 = Color(0xFF009A50)
    val green700 = Color(0xFF00733C)
    val green800 = Color(0xFF004D28)
    val green900 = Color(0xFF002614)

    val yellow100 = Color(0xFFFFF9E1)
    val yellow200 = Color(0xFFFFF4C3)
    val yellow300 = Color(0xFFFFEEA4)
    val yellow400 = Color(0xFFFFE986)
    val yellow500 = Color(0xFFFFE368)
    val yellow600 = Color(0xFFCCB653)
    val yellow700 = Color(0xFF99883E)
    val yellow800 = Color(0xFF665B2A)
    val yellow900 = Color(0xFF332D15)

    val grey100 = Color(0xFFECECEC)
    val grey200 = Color(0xFFD9D9D9)
    val grey300 = Color(0xFFC6C6C6)
    val grey400 = Color(0xFFB3B3B3)
    val grey500 = Color(0xFFA0A0A0)
    val grey600 = Color(0xFF808080)
    val grey700 = Color(0xFF606060)
    val grey800 = Color(0xFF404040)
    val grey900 = Color(0xFF202020)
    val grey1000 = Color(0xFF111111)

}

@SuppressLint("ConflictingOnColor")
internal val lightColors = lightColors(
    primary = SabotenColors.green500,
    onPrimary = SabotenColors.grey100,
    primaryVariant = SabotenColors.green500,
    secondary = SabotenColors.green500,
    secondaryVariant = SabotenColors.green500,
    onSecondary = SabotenColors.grey100,
    surface = Color.White,
    onSurface = Color.Black,
    background = SabotenColors.grey100
)

@SuppressLint("ConflictingOnColor")
internal val darkColors = darkColors(
    primary = SabotenColors.green500,
    onPrimary = SabotenColors.grey100,
    primaryVariant = SabotenColors.green500,
    secondary = SabotenColors.green500,
    secondaryVariant = SabotenColors.green500,
    surface = SabotenColors.grey700,
    onSurface = SabotenColors.grey100,
    background = SabotenColors.grey1000
)
