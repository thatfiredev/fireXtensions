package io.github.rosariopfernandes.firextensiossample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import io.github.rosariopfernandes.firextensions.database.observeChildren
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private val ref = FirebaseDatabase.getInstance().reference.child("TODOs")
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        todoAdapter = TodoAdapter(null)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = todoAdapter

        ref.observeChildren<Todo> { todos, error ->
            todos?.let {
                todoAdapter.todos = todos
                // TODO: Use Diff Util instead
                todoAdapter.notifyDataSetChanged()
            }
            error?.let {
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_LONG).show()
            }
        }

        fab.setOnClickListener { _ ->
            val dialogFragment = AddTodoDialogFragment()
            dialogFragment.show(supportFragmentManager)
        }
    }
}
