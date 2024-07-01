package com.example.movie.animation

import android.icu.number.Scale
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

fun scaleIntoContainer(
    direction: ScaleTransitionDirection = ScaleTransitionDirection.INWARDS,
    initialScale: Float = if (direction == ScaleTransitionDirection.OUTWARDS) 0.7f else 1.3f
): EnterTransition {
    return scaleIn(
        animationSpec = tween(220, delayMillis = 90),
        initialScale = initialScale
    ) + fadeIn(animationSpec = tween(220, delayMillis = 90)) +
            slideInHorizontally(
                animationSpec = tween(220, delayMillis = 90),
                initialOffsetX = { fullWidth -> if (direction == ScaleTransitionDirection.OUTWARDS) fullWidth else -fullWidth }
            )
}

fun scaleOutOfContainer(
    direction: ScaleTransitionDirection = ScaleTransitionDirection.INWARDS,
    initialScale: Float = if (direction == ScaleTransitionDirection.OUTWARDS) 0.7f else 1.3f
): ExitTransition {
    return scaleOut(
        animationSpec = tween(
            durationMillis = 220,
            delayMillis = 90
        ),
        targetScale = initialScale
    ) + fadeOut(tween(delayMillis = 90)) +
            slideOutHorizontally(
                animationSpec = tween(220, delayMillis = 90),
                targetOffsetX = { fullWidth -> if (direction == ScaleTransitionDirection.OUTWARDS) -fullWidth else fullWidth }
            )
}
enum class ScaleTransitionDirection {
    INWARDS,
    OUTWARDS
}
