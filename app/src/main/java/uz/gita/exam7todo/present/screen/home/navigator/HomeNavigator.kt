package uz.gita.exam7todo.present.screen.home.navigator

import uz.gita.exam7todo.data.model.TaskData


/**
 *    Created by Kamolov Samandar on 03.06.2023 at 8:19
 */
interface HomeNavigator {
    suspend fun navigateToAdd()
    suspend fun navigateToUpdate(task: TaskData)
}