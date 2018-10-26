package io.github.rosariopfernandes.firextensiossample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(var todos: ArrayList<Todo>?) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(v)
    }

    override fun getItemCount(): Int {
        return todos?.size ?: 0
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos!![position]
        holder.tvTitle?.text = todo.title
        holder.tvText?.text = todo.text
    }

    inner class TodoViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var tvTitle: TextView? = null
        var tvText: TextView? = null
        init {
            tvTitle = v.findViewById(R.id.tvTitle)
            tvText = v.findViewById(R.id.tvText)
        }
    }
}