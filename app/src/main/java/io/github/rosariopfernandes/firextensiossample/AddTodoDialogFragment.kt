package io.github.rosariopfernandes.firextensiossample

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.FirebaseDatabase
import io.github.rosariopfernandes.firextensions.database.push

class AddTodoDialogFragment : androidx.fragment.app.DialogFragment() {
    private lateinit var builder: AlertDialog.Builder
    private val ref = FirebaseDatabase.getInstance().reference.child("TODOs")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        builder = AlertDialog.Builder(activity)
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_add_todo, null)
        val tilTitle = view.findViewById<TextInputLayout>(R.id.tilTitle)
        val tilText = view.findViewById<TextInputLayout>(R.id.tilText)
        builder.setView(view)
        builder.setTitle(R.string.dialog_title)
        builder.setPositiveButton(R.string.action_add,
                { _, _ ->
                    val title = tilTitle.editText!!.text.toString()
                    val text = tilText.editText!!.text.toString()
                    val todo = Todo(title, text)
                    val pushKey = ref.push(todo)
                    Snackbar.make(activity!!.currentFocus,
                            "To-do $pushKey added", Snackbar.LENGTH_LONG)
                            .show()
                })
        builder.setNegativeButton(R.string.action_cancel, { _, _ ->
        })
        return builder.create()
    }

    fun show(manager: FragmentManager) {
        show(manager, "AddTODO")
    }
}