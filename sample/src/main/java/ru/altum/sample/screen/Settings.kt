package ru.altum.sample.screen

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.parcelize.Parcelize
import ru.altum.composenavigationz.Destination
import ru.altum.composenavigationz.requiredArgs
import javax.inject.Inject

@Parcelize
data class A(val name: String) : Parcelable

@Parcelize
data class B(val name: String) : Parcelable

@Parcelize
data class Test(
    val a: A,
    val b: B
) : Parcelable

@Parcelize
class Settings(val test: Test) : Destination {
    @Composable
    override fun Content() {
        SettingsScreen()
    }
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val args = savedStateHandle.requiredArgs<Settings>()
}

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green)
            .padding(16.dp)
    ) {

    }
}