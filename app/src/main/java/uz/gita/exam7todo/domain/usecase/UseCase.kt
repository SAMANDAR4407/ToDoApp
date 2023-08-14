package uz.gita.exam7todo.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.exam7todo.data.model.TaskData

/**
 *    Created by Kamolov Samandar on 29.05.2023 at 16:22
 */
interface UseCase {
    suspend fun addTask(task: TaskData)
    suspend fun update(task: TaskData)
    fun delete(task: TaskData)
    fun getTasks(): Flow<List<TaskData>>
}