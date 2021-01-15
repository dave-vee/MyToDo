package com.example.mytodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodo.room.ToDo

class ToDoListAdapter : androidx.recyclerview.widget.ListAdapter<ToDo, ToDoListAdapter.ToDoViewHolder>(ToDoComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListAdapter.ToDoViewHolder {
        return ToDoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ToDoListAdapter.ToDoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.toDo)
    }

    class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tv_todo: TextView = itemView.findViewById(R.id.tv_toDo)

        fun bind(text: String?) {
            tv_todo.text = text

        }

        companion object {
            fun create(parent: ViewGroup): ToDoViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.rv_item, parent, false)
                return ToDoViewHolder(view)
            }
        }

    }

class ToDoComparator : DiffUtil.ItemCallback<ToDo>(){
    override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
        return oldItem.toDo == newItem.toDo
    }

}

}