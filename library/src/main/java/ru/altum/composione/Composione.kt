package ru.altum.composione

import ru.altum.composione.navigator.NavigatorHolder
import ru.altum.composione.router.BaseRouter

class Composione<T : BaseRouter> private constructor(val router: T) {

    fun getNavigatorHolder(): NavigatorHolder = router.commandBuffer

    companion object {
        @JvmStatic
        fun <T : BaseRouter> create(router: T) = Composione(router)
    }
}