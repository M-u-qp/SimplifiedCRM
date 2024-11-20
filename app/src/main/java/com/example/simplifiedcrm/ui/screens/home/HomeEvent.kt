package com.example.simplifiedcrm.ui.screens.home

import com.example.simplifiedcrm.data.local.database.entity.Task

interface HomeEvent {
    fun deleteTask(task: Task)
    fun finishTask(task: Task)
}