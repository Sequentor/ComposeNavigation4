package ru.altum.sample.core.navigation

import ru.altum.composione.command.Command
import ru.altum.composione.destination.Destination

object BackToRoot : Command

class BackTo(val key: String) : Command

class FromRootTo(val destination: Destination) : Command

class NewRoot(val destination: Destination) : Command