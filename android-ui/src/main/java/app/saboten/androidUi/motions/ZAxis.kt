@file:OptIn(ExperimentalAnimationApi::class)
package app.saboten.androidUi.motions

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween


val materialTransitionZaxisIn =
    fadeIn(
        animationSpec = tween(
            210,
            90,
            easing = LinearOutSlowInEasing
        )
    ) + scaleIn(
        animationSpec = tween(210, 90, easing = LinearOutSlowInEasing),
        initialScale = 0.92f
    )

val materialTransitionZaxisOut =
    fadeOut(
        animationSpec = tween(
            90,
            easing = FastOutLinearInEasing
        )
    ) + scaleOut(
        animationSpec = tween(210, 90, easing = LinearOutSlowInEasing),
        targetScale = 0.92f)
