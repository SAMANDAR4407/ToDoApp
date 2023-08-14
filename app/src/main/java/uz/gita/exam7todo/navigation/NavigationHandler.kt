package uz.gita.exam7todo.navigation

import kotlinx.coroutines.flow.Flow

/**
 *    Created by Kamolov Samandar on 30.05.2023 at 20:39
 */
interface NavigationHandler {
    val navBuffer: Flow<NavigationArgs>
}