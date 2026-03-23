package ru.altum.composione.destination

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import ru.altum.composione.internal.decodeDestination

inline fun <reified T : Destination> key(): String = T::class.java.name

interface Destination {
    val key: String get() = this::class.java.name

    fun enterTransition(): EnterTransition = EnterTransition.None
    fun exitTransition(): ExitTransition = ExitTransition.None
    fun popEnterTransition(): EnterTransition = EnterTransition.None
    fun popExitTransition(): ExitTransition = ExitTransition.None
    fun sizeTransform(): SizeTransform = SizeTransform(false)

    @Composable
    fun Content()
}

inline fun <reified T : Destination> SavedStateHandle.requiredArgs(): T {
    val destination = keys().firstNotNullOfOrNull { key ->
        get<String>(key)?.let { json ->
            decodeDestination(json)
        }
    }
    return requireNotNull(destination as? T)
}

