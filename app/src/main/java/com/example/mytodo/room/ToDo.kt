package com.example.mytodo.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_toDo")
class ToDo(@PrimaryKey @ColumnInfo(name = "ToDo") val toDo : String, val isChecked : Boolean ) {
}