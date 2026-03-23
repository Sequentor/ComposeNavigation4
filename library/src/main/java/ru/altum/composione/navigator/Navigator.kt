package ru.altum.composione.navigator

import ru.altum.composione.command.Command
import ru.altum.composione.destination.Destination

interface Navigator {
    val backStack: Collection<Destination>

    fun applyCommands(commands: Array<out Command>)
}