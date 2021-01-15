package com.example.mytodo


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodo.room.ToDo
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

     var todoAdapter = ToDoListAdapter()

    private val newWordActivityRequestCode = 1

    private val toDoViewModel : ToDoViewModel by viewModels {
        ViewModelFactory((application as ToDoApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView : RecyclerView = findViewById(R.id.recyclerview)
        val adapter = ToDoListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        toDoViewModel.allToDos.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.submitList(it) }
        })


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewToDoActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewToDoActivity.EXTRA_REPLY)?.let { reply ->
                val toDo = ToDo(reply, 0)
                toDoViewModel.insert(toDo)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.close_app -> finish()
            R.id.delete_all -> AlertDialog.Builder(this)
                .setPositiveButton("Yes") { _, _ ->
                    toDoViewModel.deleteAllToDos()
                }
                .setNegativeButton("No") { _, _ -> }
                .setTitle("Delete All Items")
                .setMessage("Are You Sure You Want to Delete All Items")
                .create().show()

        }
        return super.onOptionsItemSelected(item)
    }

    var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean {

                return false // true if moved, false otherwise
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //Remove swiped item from list and notify the RecyclerView
                todoAdapter.notifyItemRemoved(viewHolder.layoutPosition)

            }
        }


}