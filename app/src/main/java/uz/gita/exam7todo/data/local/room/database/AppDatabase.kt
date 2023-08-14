package uz.gita.exam7todo.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.exam7todo.data.local.room.dao.TaskDao
import uz.gita.exam7todo.data.local.room.entity.TaskEntity

/**
 *    Created by Kamolov Samandar on 26.05.2023 at 16:42
 */

@Database(entities = [TaskEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getDao(): TaskDao
}