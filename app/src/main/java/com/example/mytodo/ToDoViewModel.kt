package com.example.mytodo

import androidx.lifecycle.*
import com.example.mytodo.room.ToDo
import com.example.mytodo.room.ToDoRepository
import kotlinx.coroutines.launch

class ToDoViewModel(private val repo :ToDoRepository) :ViewModel() {

    //get a cached list of all todos
    val allToDos : LiveData<List<ToDo>> = repo.toDo.asLiveData()

    fun insert (toDo: ToDo) = viewModelScope.launch {
        repo.insert(toDo)
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