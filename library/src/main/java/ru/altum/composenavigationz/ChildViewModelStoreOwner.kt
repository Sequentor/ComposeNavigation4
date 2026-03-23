package ru.altum.composenavigationz

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.SAVED_STATE_REGISTRY_OWNER_KEY
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.enableSavedStateHandles
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.savedstate.SavedStateRegistryOwner

@Composable
fun rememberChildViewModelStoreOwner(
    destination: Destination,
    viewModelStore: ViewModelStore,
    savedStateRegistryOwner: SavedStateRegistryOwner
): ViewModelStoreOwner = remember(
    destination.key,
    viewModelStore,
    savedStateRegistryOwner
) {
    object : ViewModelStoreOwner,
        SavedStateRegistryOwner by savedStateRegistryOwner,
        HasDefaultViewModelProviderFactory {

        override val viewModelStore: ViewModelStore
            get() = viewModelStore

        override val defaultViewModelProviderFactory: ViewModelProvider.Factory
            get() = SavedStateViewModelFactory()

        override val defaultViewModelCreationExtras: CreationExtras
            get() = MutableCreationExtras().also {
                it[SAVED_STATE_REGISTRY_OWNER_KEY] = this
                it[VIEW_MODEL_STORE_OWNER_KEY] = this
                it[DEFAULT_ARGS_KEY] = Bundle().apply {
                    putParcelable(destination.key, destination)
                }
            }

        init {
            require(this.lifecycle.currentState == Lifecycle.State.INITIALIZED) {
                "The Lifecycle state is already beyond INITIALIZED. Current state: ${this.lifecycle.currentState}"
            }
            enableSavedStateHandles()
        }
    }
}