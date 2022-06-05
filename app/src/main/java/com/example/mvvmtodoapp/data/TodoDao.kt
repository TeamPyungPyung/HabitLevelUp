package com.example.mvvmtodoapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    //suspend means asynchronous programming.
    //until suspend fun done, stop doing.

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getTodoById(id: Int): Todo?

    //get realtime Todos.
    @Query("SELECT * FROM todo")
    fun getTodos(): Flow<List<Todo>>
}

