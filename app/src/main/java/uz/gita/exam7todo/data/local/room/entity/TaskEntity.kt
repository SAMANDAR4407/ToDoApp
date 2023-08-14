package uz.gita.exam7todo.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.gita.exam7todo.data.model.TaskData
import java.util.*

/**
 *    Created by Kamolov Samandar on 03.06.2023 at 15:21
 */
@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val topic: String,
    val date: String,
    val time: String,
    val uuid: UUID,
    val done: Boolean = false
){
    fun toData() = TaskData(id, title, topic, date, time, uuid, done)
}