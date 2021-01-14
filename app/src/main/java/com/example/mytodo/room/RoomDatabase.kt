package com.example.mytodo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope


@Database( entities = [ToDo::class],version = 1,exportSchema = false)
abstract class ToDoRoomDatabase : RoomDatabase() {

    abstract fun toDoDao() : MyDao

    companion object{

        @Volatile
        private var INSTANCE: ToDoRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope) : ToDoRoomDatabase {

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoRoomDatabase::class.java,
                    "ToDo_Database"
                ).build()

                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}
