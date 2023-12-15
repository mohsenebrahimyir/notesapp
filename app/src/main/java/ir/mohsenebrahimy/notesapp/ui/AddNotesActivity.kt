package ir.mohsenebrahimy.notesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ir.mohsenebrahimy.notesapp.R
import ir.mohsenebrahimy.notesapp.databinding.ActivityAddNotesBinding
import ir.mohsenebrahimy.notesapp.db.DBHelper
import ir.mohsenebrahimy.notesapp.db.dao.NotesDao
import ir.mohsenebrahimy.notesapp.db.model.DBNotesModel

class AddNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNotesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = NotesDao(DBHelper(this))

        binding.btnSave.setOnClickListener {
            val title = binding.edtTitleNotes.text.toString()
            val detail = binding.edtDetailNotes.text.toString()

            if (title.isEmpty()) {
                Toast.makeText(
                    this,
                    R.string.title_notes_empty_error,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val notes = DBNotesModel(0, title, detail, "0", getDate())
                val result = dao.saveNotes(notes)

                if (result) {
                    Toast.makeText(
                        this,
                        R.string.save_notes_successful,
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        R.string.save_notes_unsuccessful,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.btnCancel.setOnClickListener { finish() }
    }

    private fun getDate(): String {
        return "test"
    }
}