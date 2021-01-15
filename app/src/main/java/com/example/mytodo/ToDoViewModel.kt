package com.example.mytodo

import androidx.lifecycle.*
import com.example.mytodo.room.ToDo
import com.example.mytodo.room.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(private val repo :ToDoRepository) :ViewModel() {

    //get a cached list of all todos
    val allToDos : LiveData<List<ToDo>> = repo.toDo.asLiveData()

    fun insert (toDo: ToDo) = viewModelScope.launch {
        repo.insert(toDo)
    }


    fun deleteAllToDos() = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteAll()
    }

    fun deleteToDo(toDo: ToDo) = viewModelScope.launch {
        repo.deleteToDo(toDo)
    }

}

class ViewModelFactory(private val repo: ToDoRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToDoViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }

}