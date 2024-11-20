package com.example.simplifiedcrm.ui.screens.tasks

import com.example.simplifiedcrm.data.local.database.entity.Task

interface TasksEvent {
    fun deleteTask(task: Task)
}