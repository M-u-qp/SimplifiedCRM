package com.example.simplifiedcrm.ui.screens.task_creation

import com.example.simplifiedcrm.data.local.database.entity.Task

interface TaskCreationEvent {
    fun createTask()
    fun deleteTask(task: Task)
    fun resetError()
    fun updateClientName(name: String)
    fun updateClientPhone(phone: String)
    fun updateClientEmail(email: String)
    fun updateClientMarking(marking: String)
    fun updateDescription(description: String)
    fun updateProductName(productName: String)
    fun updateProductPrice(productPrice: Long)
    fun updateDeliveryName(name: String)
    fun updateDeliveryPrice(price: Long)
}