package com.example.vjezba3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var titleEdit: EditText
    private lateinit var contentEdit: EditText
    private lateinit var saveBtn: Button
    private lateinit var deleteBtn: Button
    private var noteId: Int? = null
    private lateinit var database: NoteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        database = NoteDatabase.getDatabase(this)

        titleEdit = findViewById(R.id.editTitle)
        contentEdit = findViewById(R.id.editContent)
        saveBtn = findViewById(R.id.btnSave)
        deleteBtn = findViewById(R.id.btnDelete)

        noteId = intent.getIntExtra("noteId", -1).takeIf { it != -1 }

        if (noteId != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val note = database.noteDao().getAllNotes().find { it.id == noteId }
                note?.let {
                    runOnUiThread {
                        titleEdit.setText(it.title)
                        contentEdit.setText(it.content)
                    }
                }
            }
        } else {
            deleteBtn.isEnabled = false
        }

        saveBtn.setOnClickListener {
            val title = titleEdit.text.toString()
            val content = contentEdit.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                if (noteId != null) {
                    database.noteDao().updateNote(Note(noteId!!, title, content))
                } else {
                    database.noteDao().insertNote(Note(title = title, content = content))
                }
                finish()
            }
        }

        deleteBtn.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Brisanje bilješke")
                .setMessage("Jeste li sigurni da želite obrisati ovu bilješku?")
                .setPositiveButton("Da") { _, _ ->
                    CoroutineScope(Dispatchers.IO).launch {
                        database.noteDao().deleteNote(
                            Note(noteId!!, titleEdit.text.toString(), contentEdit.text.toString())
                        )
                        finish()
                    }
                }
                .setNegativeButton("Ne", null)
                .show()
        }
    }
}
