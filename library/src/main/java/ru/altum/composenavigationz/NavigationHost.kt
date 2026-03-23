package ru.altum.composenavigationz

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.savedstate.compose.LocalSavedStateRegistryOwner

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    backStack: SnapshotStateList<Destination>,
    onBack: () -> Unit,
) {
    val previousKeys = remember { mutableStateOf(emptySet<String>()) }
    val previousSize = remember { mutableIntStateOf(backStack.size) }

    val isForward = remember(backStack.size) {
        backStack.size > previousSize.intValue
    }.also {
        previousSize.intValue = backStack.size
    }

    val saveableStateHolder = rememberSaveableStateHolder()

    val rootViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    val rootViewModelStore = rootViewModelStoreOwner.viewModelStore

    BackHandler(enabled = backStack.size > 1, onBack = onBack)

    val destination: Destination = backStack.last()

    val destinationViewModelStore = remember(destination.key) {
        rootViewModelStore
            .getEntryViewModel()
            .viewModelStoreForKey(destination.key)
    }

    AnimatedContent(
        modifier = modifier,
        targetState = destination,
        transitionSpec = {
            if (isForward) {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(300)
                ).togetherWith(
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(300)
                    )
                )
            } else {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                ).togetherWith(
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(300)
                    )
                )
            }.using(
                SizeTransform(clip = false)
            )
        }
    ) { destination ->
        saveableStateHolder.SaveableStateProvider(destination.key) {
            val childViewModelStoreOwner = rememberChildViewModelStoreOwner(
                destination = destination,
                viewModelStore = destinationViewModelStore,
                savedStateRegistryOwner = LocalSavedStateRegistryOwner.current
            )
            CompositionLocalProvider(LocalViewModelStoreOwner provides childViewModelStoreOwner) {
                destination.Content()
            }
        }
    }

    LaunchedEffect(backStack.size) {
        val currentKeys: Set<String> = backStack.map { it.key }.toSet()
        val removedKeys: Set<String> = previousKeys.value - currentKeys
        if (removedKeys.isNotEmpty()) {
            rootViewModelStore.getEntryViewModel().clearViewModelStoreOwnersForKeys(removedKeys)
        }
        previousKeys.value = currentKeys
    }
}