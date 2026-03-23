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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.parcelize.Parcelize
import ru.altum.composenavigationz.Destination
import ru.altum.sample.Navigator
import kotlin.random.Random

@Parcelize
object Home : Destination {
    @Composable
    override fun Content() {
        HomeScreen()
    }
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val navigator: Navigator,
) : ViewModel() {
    private val _textState = MutableStateFlow("")
    val textState: StateFlow<String> = _textState

    fun onNext() = navigator.navigateTo(Profile(Random.nextFloat().toString()))
    fun onTextChanged(text: String) = _textState.update { text }
}

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val text by viewModel.textState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { viewModel.onTextChanged(it) })
        Spacer(modifier = Modifier.weight(1f))
        Button(
            content = { Text("Click") },
            onClick = { viewModel.onNext() }
        )
    }
}