package ru.altum.composenavigationz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

internal class EntryViewModel : ViewModel() {
    private val owners = mutableMapOf<String, ViewModelStore>()

    fun viewModelStoreForKey(key: String): ViewModelStore =
        owners.getOrPut(key) { ViewModelStore() }

    fun clearViewModelStoreOwnersForKeys(keys: Set<String>) {
        keys.forEach { key -> owners.remove(key)?.clear() }
    }

    override fun onCleared() {
        owners.forEach { (_, store) -> store.clear() }
    }
}

internal fun ViewModelStore.getEntryViewModel(): EntryViewModel {
    val provider = ViewModelProvider.create(
        store = this,
        factory = viewModelFactory { initializer { EntryViewModel() } },
    )
    return provider[EntryViewModel::class]
}