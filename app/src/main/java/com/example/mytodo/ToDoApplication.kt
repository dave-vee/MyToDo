package com.example.mytodo

import android.app.Application
import com.example.mytodo.room.ToDoRepository
import com.example.mytodo.room.ToDoRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class ToDoApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { ToDoRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy {  ToDoRepository(database.toDoDao()) }

}