package com.example.simplifiedcrm.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simplifiedcrm.data.local.database.entity.Task
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM task_table ORDER BY timestamp DESC")
    fun getAllTasksSortByDate(): PagingSource<Int, Task>

    @Query("SELECT * FROM task_table WHERE statusTask = :statusTask")
    fun getAllTasksSortByStatus(statusTask: String): PagingSource<Int, Task>

    @Query(
        "SELECT TOTAL(productPrice) FROM task_table WHERE" +
        "(:fromDate IS NULL OR timestamp >= :fromDate) AND" +
        "(:toDate IS NULL OR timestamp <= :toDate) "
    )
    fun getTotalPrice(fromDate: Date?, toDate: Date?): Flow<Long>
}