package uz.gita.exam7todo.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import uz.gita.exam7todo.data.local.room.dao.TaskDao
import uz.gita.exam7todo.data.model.TaskData
import javax.inject.Inject

/**
 *    Created by Kamolov Samandar on 26.05.2023 at 16:49
 */

class AppRepositoryImpl @Inject constructor(
    private val dao: TaskDao
): AppRepository {

    override suspend fun add(task: TaskData) {
        withContext(Dispatchers.IO){
            dao.addTask(task.toEntity())
        }
    }

    override fun delete(task: TaskData) {
        dao.delete(task.toEntity().id)
    }

    override suspend fun update(task: TaskData) {
        withContext(Dispatchers.IO){
            dao.updateTask(task.toEntity())
        }
    }

    override fun getTasks(): Flow<List<TaskData>> =
        dao.getTasks().map {
            it.map { entity ->
                entity.toData()
            }
        }
}