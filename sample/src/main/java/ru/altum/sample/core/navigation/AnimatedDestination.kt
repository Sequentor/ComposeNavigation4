package ru.altum.sample.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import ru.altum.composione.destination.Destination

interface AnimatedDestination : Destination {

    override fun enterTransition(): EnterTransition = slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween()
    )

    override fun exitTransition(): ExitTransition = slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween()
    )

    override fun popEnterTransition(): EnterTransition = slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = tween()
    )

    override fun popExitTransition(): ExitTransition = slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween()
    )
}