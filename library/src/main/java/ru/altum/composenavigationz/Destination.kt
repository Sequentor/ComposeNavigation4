package ru.altum.composenavigationz

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle

interface Destination : Parcelable {
    val key: String get() = this::class.java.name

    @Composable
    fun Content()
}

inline fun <reified T : Destination> key(): String = T::class.java.name

inline fun <reified T : Destination> SavedStateHandle.optionalArgs(): T? = try {
    get(key<T>())
} catch (_: Exception) {
    null
}

inline fun <reified T : Destination> SavedStateHandle.requiredArgs(): T =
    requireNotNull(get(key<T>()))