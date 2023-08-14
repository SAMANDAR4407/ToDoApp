package uz.gita.exam7todo.data.local.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.gita.exam7todo.data.local.room.entity.TaskEntity

/**
 *    Created by Kamolov Samandar on 03.06.2023 at 15:34
 */

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTask(entity: TaskEntity)

    @Update
    fun updateTask(entity: TaskEntity)

    @Query("delete from tasks where id = :id")
    fun delete(id: Int)

    @Query("select * from tasks order by id desc")
    fun getTasks(): Flow<List<TaskEntity>>
}