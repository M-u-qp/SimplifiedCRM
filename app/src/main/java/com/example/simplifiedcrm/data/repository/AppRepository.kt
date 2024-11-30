package com.example.simplifiedcrm.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.simplifiedcrm.data.local.database.TaskDao
import com.example.simplifiedcrm.data.local.database.entity.Task
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    suspend fun insertTask(task: Task) = taskDao.insertTask(task)
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
    fun getSortedPagedAllTask() =
        Pager(config = PagingConfig(pageSize = 10)) { taskDao.getAllTasksSortByDate() }

    fun getTotalPrice(fromDate: Date? = null, toDate: Date? = null): Flow<Long> =
        taskDao.getTotalPrice(fromDate, toDate)

    fun getTotalPricePerDay(fromDate: Date? = null, toDate: Date? = null): Flow<List<Task>> =
        taskDao.getTotalPricePerDay(fromDate, toDate)

    fun getSortedPagedTaskByStatus(statusTask: String) =
        Pager(config = PagingConfig(pageSize = 10)) { taskDao.getAllTasksSortByStatus(statusTask) }
}