package uz.gita.exam7todo.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.exam7todo.data.model.TaskData
import uz.gita.exam7todo.domain.AppRepository
import javax.inject.Inject

/**
 *    Created by Kamolov Samandar on 29.05.2023 at 16:25
 */
class UseCaseImpl @Inject constructor(
    private val repository: AppRepository
) : UseCase {
    override suspend fun addTask(task: TaskData) {
        repository.add(task)
    }

    override suspend fun update(task: TaskData) {
        repository.update(task)
    }

    override fun delete(task: TaskData) {
        repository.delete(task)
    }

    override fun getTasks(): Flow<List<TaskData>> {
        return repository.getTasks()
    }
}