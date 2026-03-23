package ru.altum.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ru.altum.composenavigationz.NavigationHost
import ru.altum.sample.ui.theme.ComposeNavigation4Theme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavigation4Theme {
                Surface {
                    NavigationHost(
                        modifier = Modifier
                            .statusBarsPadding()
                            .navigationBarsPadding(),
                        backStack = navigator.backStack,
                        onBack = { navigator.onBack() },
                    )
                }
            }
        }
    }
}