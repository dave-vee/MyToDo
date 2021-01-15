package com.example.mytodo.room

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database( entities = [ToDo::class],version = 1,exportSchema = false)
abstract class ToDoRoomDatabase : RoomDatabase() {

    abstract fun toDoDao() : MyDao


    private class ToDoDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var toDoDao = database.toDoDao()

                    // Delete all content here.
                    toDoDao.deleteAll()

                    // Add sample words.
                    var todo = ToDo("Go to Paris",false)
                    toDoDao.insert(todo)
                    todo = ToDo("Go to Beijing ", true)
                    toDoDao.insert(todo)
                    todo = ToDo("Go to Nairobi ", true)
                    toDoDao.insert(todo)
                    todo = ToDo("Go to Mombasa ", true)
                    toDoDao.insert(todo)
                }
            }
        }
    }

    companion object{

        @Volatile
        private var INSTANCE: ToDoRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope) : ToDoRoomDatabase {

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoRoomDatabase::class.java,
                    "ToDo_Database"

                ) .addCallback(ToDoDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}
