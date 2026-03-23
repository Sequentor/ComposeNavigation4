package ru.altum.composione.router

import ru.altum.composione.command.Command
import ru.altum.composione.command.CommandBuffer

abstract class BaseRouter {
    internal val commandBuffer = CommandBuffer()

    protected fun executeCommands(vararg commands: Command) {
        commandBuffer.executeCommands(commands)
    }
}