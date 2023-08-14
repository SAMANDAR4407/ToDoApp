package uz.gita.exam7todo

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import uz.gita.exam7todo.navigation.NavigationHandler
import uz.gita.exam7todo.present.screen.home.HomeScreen
import uz.gita.exam7todo.ui.theme.Exam7ToDoTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationHandler: NavigationHandler
    private val myPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestPermission()) {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Exam7ToDoTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Navigator(screen = HomeScreen()) { navigator ->
                        LaunchedEffect(key1 = navigator) {
                            navigationHandler.navBuffer
                                .onEach { it(navigator) }
                                .collect()
                        }
                        CurrentScreen()
                    }
                }
            }
        }
        requestPermission()
    }
    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            myPermissionRequest.launch(
                Manifest.permission.POST_NOTIFICATIONS
            )
        }
    }
}
