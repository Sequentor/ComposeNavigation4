package ru.altum.sample

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import ru.altum.composenavigationz.Destination
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor(startDestination: Destination) {

    val backStack: SnapshotStateList<Destination> = mutableStateListOf(startDestination)

    fun navigateTo(key: Destination) {
        backStack.add(key)
    }

    fun onBack() {
        backStack.removeAt(backStack.lastIndex)
    }
}