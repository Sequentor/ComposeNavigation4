package ru.altum.sample.core.navigation

import ru.altum.composione.destination.Destination
import ru.altum.composione.router.Router

class AppRouter : Router() {

    fun backTo(key: String) {
        executeCommands(BackTo(key))
    }

    fun backToRoot() {
        executeCommands(BackToRoot)
    }

    fun setNewRoot(destination: Destination) {
        executeCommands(NewRoot(destination))
    }

    fun navigateFromRootTo(destination: Destination) {
        executeCommands(FromRootTo(destination))
    }
}