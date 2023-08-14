package uz.gita.exam7todo.present.screen.home.navigator

import android.util.Log
import uz.gita.exam7todo.data.model.TaskData
import uz.gita.exam7todo.navigation.AppNavigator
import uz.gita.exam7todo.present.screen.add.AddScreen
import uz.gita.exam7todo.present.screen.edit.UpdateScreen
import javax.inject.Inject

/**
 *    Created by Kamolov Samandar on 03.06.2023 at 8:19
 */
class HomeNavigatorImpl @Inject constructor(
    private val navigator: AppNavigator
): HomeNavigator {
    override suspend fun navigateToAdd(){
        navigator.navigateTo(AddScreen())
    }
    override suspend fun navigateToUpdate(task: TaskData){
        Log.d("TTT", "navigator update:")
        navigator.navigateTo(UpdateScreen(task))
    }
}