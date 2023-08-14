package uz.gita.exam7todo.present.screen.home.viewmodel

import kotlinx.coroutines.flow.StateFlow
import uz.gita.exam7todo.data.model.TaskData

/**
 *    Created by Kamolov Samandar on 30.05.2023 at 13:15
 */

interface HomeContract {
    sealed interface Intent {
        data class OpedEditScreen(val task: TaskData) : Intent
        data class Update(val task: TaskData) : Intent
        data class Delete(val task: TaskData) : Intent
        object OpenAddScreen : Intent
    }

    data class UIState(
        val tasks: List<TaskData> = listOf(),
        val currentData: TaskData? = null
    )

    interface ViewModel{
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)
    }
}