package uz.gita.exam7todo.domain

import kotlinx.coroutines.flow.Flow
import uz.gita.exam7todo.data.model.TaskData

/**
 *    Created by Kamolov Samandar on 26.05.2023 at 16:48
 */

interface AppRepository {

    suspend fun add(task: TaskData)
    fun delete(task: TaskData)
    suspend fun update(task: TaskData)
    fun getTasks(): Flow<List<TaskData>>
}