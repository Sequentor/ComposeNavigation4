package ru.altum.composione.router

import ru.altum.composione.command.Back
import ru.altum.composione.command.Forward
import ru.altum.composione.destination.Destination

open class Router : BaseRouter() {

    fun back() {
        executeCommands(Back())
    }

    fun navigateTo(destination: Destination) {
        executeCommands(Forward(destination))
    }
}