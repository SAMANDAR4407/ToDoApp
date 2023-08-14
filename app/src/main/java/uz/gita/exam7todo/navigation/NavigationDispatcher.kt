package uz.gita.exam7todo.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 *    Created by Kamolov Samandar on 30.05.2023 at 20:38
 */

@Singleton
class NavigationDispatcher @Inject constructor() : AppNavigator, NavigationHandler {

    override val navBuffer = MutableSharedFlow<NavigationArgs>()

    private suspend fun navigate(args: NavigationArgs) {
        navBuffer.emit(args)
    }

    override suspend fun navigateTo(screen: AppScreen) {
        this.navigate { push(screen) }
    }

    override suspend fun back() = navigate {
        pop()
    }
}