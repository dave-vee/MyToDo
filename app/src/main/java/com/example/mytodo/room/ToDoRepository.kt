package com.example.mytodo.room

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class ToDoRepository(private val todoDao : MyDao) {

    //get all ToDos
    val toDo : Flow<List<ToDo>> =  todoDao.getList()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(todo: ToDo) = todoDao.insert(todo)

}