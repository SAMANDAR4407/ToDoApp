package uz.gita.exam7todo.present.screen.add.navigator

import uz.gita.exam7todo.navigation.AppNavigator
import javax.inject.Inject

/**
 *    Created by Kamolov Samandar on 03.06.2023 at 7:59
 */
class AddNavigatorImpl @Inject constructor(
    private val navigator: AppNavigator
): AddNavigator {
    override suspend fun pop(){
        navigator.back()
    }
}