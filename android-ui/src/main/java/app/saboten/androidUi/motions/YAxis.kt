package app.hdj.datepick.ui.animation

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.dp


fun <S> materialTransitionSpecYaxisPopFromBottom(): AnimatedContentScope<S>.() -> ContentTransform = {
    materialTransitionYaxisIn() with materialTransitionYaxisOut(false)
}

fun materialTransitionYaxisIn(inFromBottom: Boolean = true) =
    fadeIn(
        animationSpec = tween(
            210,
            90,
            easing = LinearOutSlowInEasing
        )
    ) + slideInVertically(
        animationSpec = tween(300, easing = LinearOutSlowInEasing),
        initialOffsetY = {
            if (inFromBottom) 30.dp.value.toInt()
            else -30.dp.value.toInt()
        }
    )

fun materialTransitionYaxisOut(outToTop: Boolean = true) =
    fadeOut(
        animationSpec = tween(
            90,
            easing = LinearOutSlowInEasing
        )
    ) + slideOutVertically(
        animationSpec = tween(300, easing = LinearOutSlowInEasing),
        targetOffsetY = {
            if (outToTop) -30.dp.value.toInt()
            else 30.dp.value.toInt()
        }
    )
