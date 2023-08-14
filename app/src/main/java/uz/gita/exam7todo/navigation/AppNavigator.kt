package uz.gita.exam7todo.navigation

/**
 *    Created by Kamolov Samandar on 30.05.2023 at 20:37
 */
interface AppNavigator {

    suspend fun navigateTo(screen: AppScreen)
    suspend fun back()
}