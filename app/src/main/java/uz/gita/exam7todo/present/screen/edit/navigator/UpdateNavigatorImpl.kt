package uz.gita.exam7todo.present.screen.edit.navigator

import uz.gita.exam7todo.navigation.AppNavigator
import javax.inject.Inject

/**
 *    Created by Kamolov Samandar on 03.06.2023 at 7:59
 */
class UpdateNavigatorImpl @Inject constructor(
    private val navigator: AppNavigator
): UpdateNavigator {
    override suspend fun pop(){
        navigator.back()
    }
}