package com.example.mytodo.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {
    @Query("SELECT * FROM table_toDo ORDER BY Todo DESC;")
    fun getList( ) : Flow<List<ToDo>>

    @Insert( onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo : ToDo)

    @Query("DELETE FROM table_toDo")
    fun deleteAll()
}