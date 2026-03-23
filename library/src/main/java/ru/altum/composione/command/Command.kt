package ru.altum.composione.command

import ru.altum.composione.destination.Destination

interface Command

class Back : Command

class Forward(val destination: Destination) : Command