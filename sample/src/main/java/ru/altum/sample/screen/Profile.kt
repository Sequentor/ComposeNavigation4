package ru.altum.sample.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.parcelize.Parcelize
import ru.altum.composenavigationz.Destination
import ru.altum.composenavigationz.requiredArgs
import ru.altum.sample.Navigator

@Parcelize
class Profile(val name: String) : Destination {
    @Composable
    override fun Content() {
        ProfileScreen()
    }
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val navigator: Navigator,
) : ViewModel() {

    val args = savedStateHandle.requiredArgs<Profile>()

    fun onNext() {
        navigator.navigateTo(
            Settings(
                Test(
                    a = A(name = "nammmeee"),
                    b = B(name = "dfdsfsd")
                )
            )
        )
    }
}

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    val text = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text.value,
            onValueChange = { text.value = it })
        Spacer(modifier = Modifier.weight(1f))
        Button(
            content = { Text("Click") },
            onClick = { viewModel.onNext() }
        )
    }
}