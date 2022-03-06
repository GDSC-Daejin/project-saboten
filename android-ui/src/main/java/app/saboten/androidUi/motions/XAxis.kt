package app.saboten.androidUi.motions

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.unit.dp

fun materialTransitionXaxisIn(inToRight: Boolean = true) =
    fadeIn(
        animationSpec = tween(
            210,
            90,
            easing = LinearOutSlowInEasing
        )
    ) + slideInHorizontally(
        animationSpec = tween(300, easing = LinearOutSlowInEasing),
        initialOffsetX = {
            if (inToRight) 30.dp.value.toInt()
            else -30.dp.value.toInt()
        }
    )

fun materialTransitionXaxisOut(outToLeft: Boolean = true) =
    fadeOut(
        animationSpec = tween(
            90,
            easing = LinearOutSlowInEasing
        )
    ) + slideOutHorizontally(
        animationSpec = tween(300, easing = LinearOutSlowInEasing),
        targetOffsetX = {
            if (outToLeft) -30.dp.value.toInt()
            else 30.dp.value.toInt()
        }
    )