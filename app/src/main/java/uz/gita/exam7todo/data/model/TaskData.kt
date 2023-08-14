package uz.gita.exam7todo.data.model

import uz.gita.exam7todo.data.local.room.entity.TaskEntity
import java.util.*

/**
 *    Created by Kamolov Samandar on 03.06.2023 at 15:12
 */

data class TaskData(
    val id: Int = 0,
    val title: String,
    val topic: String,
    val date: String,
    val time: String,
    val uuid: UUID,
    val done: Boolean = false
){
    fun toEntity() = TaskEntity(id, title, topic, date, time, uuid, done)
}