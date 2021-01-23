package com.example.mytodo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodo.room.ToDo
import kotlinx.android.synthetic.main.activity_new_to_do.view.*
import kotlinx.android.synthetic.main.rv_item.view.*

class ToDoListAdapter : RecyclerView.Adapter<ToDoListAdapter.ToDoViewHolder>() {

    inner class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<ToDo>() {
        override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        return ToDoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val todo = differ.currentList[position]
        holder.itemView.apply {
            tv_toDo.text = todo.toDo

        }


    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}