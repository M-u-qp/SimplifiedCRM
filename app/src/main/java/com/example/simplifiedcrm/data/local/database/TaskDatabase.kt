package com.example.simplifiedcrm.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.simplifiedcrm.data.local.database.convertor.DBConvertors
import com.example.simplifiedcrm.data.local.database.entity.Task

@Database(entities = [Task::class], version = 1)
@TypeConverters(DBConvertors::class)
abstract class TaskDatabase : RoomDatabase() {
    companion object {
        const val TASK_DB_NAME = "task_db_name"
    }

    abstract val taskDao: TaskDao
}