package ru.altum.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ru.altum.composione.Composione
import ru.altum.composione.navhost.NavHost
import ru.altum.sample.core.navigation.AppNavigator
import ru.altum.sample.core.navigation.AppRouter
import ru.altum.sample.ui.theme.SampleTheme
import javax.inject.Inject

@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appNavigator: AppNavigator

    @Inject
    lateinit var composione: Composione<AppRouter>

    @Inject
    lateinit var appRouter: AppRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleTheme {
                Surface {
                    NavHost(
                        modifier = Modifier
                            .statusBarsPadding()
                            .navigationBarsPadding(),
                        backStack = appNavigator.backStack,
                        onBack = { appRouter.back() },
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        composione.getNavigatorHolder().setNavigator(appNavigator)
    }

    override fun onPause() {
        composione.getNavigatorHolder().removeNavigator()
        super.onPause()
    }
}