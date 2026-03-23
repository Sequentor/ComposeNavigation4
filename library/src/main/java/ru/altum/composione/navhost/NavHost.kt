package ru.altum.composione.navhost

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import ru.altum.composione.destination.Destination
import ru.altum.composione.internal.ViewModelHolder
import ru.altum.composione.internal.rememberChildViewModelStoreOwner
import ru.altum.composione.internal.rememberViewModelHolder

@Composable
fun <T : Destination> NavHost(
    modifier: Modifier = Modifier,
    backStack: Collection<T>,
    onBack: () -> Unit,
) {
    val saveableStateHolder = rememberSaveableStateHolder()
    val viewModelHolder: ViewModelHolder = rememberViewModelHolder()

    val currentBackStackKeys by rememberUpdatedState(backStack.map { it.key })
    val previousBackStackKeys = remember { mutableStateOf(listOf<String>()) }

    val isForward: Boolean = remember(currentBackStackKeys, previousBackStackKeys) {
        when {
            currentBackStackKeys.size > previousBackStackKeys.value.size -> true
            currentBackStackKeys.size == previousBackStackKeys.value.size -> true
            else -> false
        }.also {
            previousBackStackKeys.value = currentBackStackKeys
        }
    }

    BackHandler(enabled = backStack.size > 1, onBack = onBack)

    val destination: Destination = backStack.last()

    AnimatedContent(
        modifier = modifier,
        targetState = destination,
        transitionSpec = {
            when {
                isForward -> destination.enterTransition() togetherWith destination.exitTransition()
                else -> destination.popEnterTransition() togetherWith destination.popExitTransition()
            }.using(destination.sizeTransform())
        }
    ) { destination ->
        DisposableEffect(destination.key) {
            onDispose {
                val isRemoved = !currentBackStackKeys.contains(destination.key)
                if (isRemoved) {
                    viewModelHolder.clearViewModelStoreOwnerForKey(destination.key)
                    saveableStateHolder.removeState(destination.key)
                }
            }
        }

        saveableStateHolder.SaveableStateProvider(destination.key) {
            val childViewModelStoreOwner = rememberChildViewModelStoreOwner(
                destination = destination,
                viewModelHolder = viewModelHolder,
            )
            CompositionLocalProvider(LocalViewModelStoreOwner provides childViewModelStoreOwner) {
                destination.Content()
            }
        }
    }
}