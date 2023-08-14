package uz.gita.exam7todo.present.screen.edit.viewmodel

import kotlinx.coroutines.flow.StateFlow
import uz.gita.exam7todo.data.model.TaskData

/**
 *    Created by Kamolov Samandar on 29.05.2023 at 15:14
 */

interface UpdateContract {

    sealed interface Intent {
        data class Save(val task: TaskData) : Intent
        data class Delete(val task: TaskData) : Intent
        object Pop : Intent
    }

    data class UiState(
        val task: TaskData? = null
    )

    interface ViewModel {
        val uiState: StateFlow<UiState>
        fun eventDispatcher(intent: Intent)
    }
}