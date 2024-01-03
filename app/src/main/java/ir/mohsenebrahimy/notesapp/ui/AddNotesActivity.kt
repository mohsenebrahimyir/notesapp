package ir.mohsenebrahimy.notesapp.ui

import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ir.mohsenebrahimy.notesapp.R
import ir.mohsenebrahimy.notesapp.data.local.db.DBHandler
import ir.mohsenebrahimy.notesapp.data.model.NotesEntity
import ir.mohsenebrahimy.notesapp.databinding.ActivityAddNotesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import saman.zamani.persiandate.PersianDate

class AddNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNotesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("notesId", 0)
        val type = intent.getBooleanExtra("newNotes", false)

        val db = DBHandler.getDatabase(this)

        if (type) {
            binding.txtDate.text = getDate()
        } else {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val notes = db.noteDao().getNoteById(id)
                    val edit = Editable.Factory()
                    withContext(Dispatchers.Main) {
                        binding.edtTitleNotes.text = edit.newEditable(notes.title)
                        binding.edtDetailNotes.text = edit.newEditable(notes.detail)
                        binding.txtDate.text = notes.date
                    }
                }
            }
        }

        binding.btnSave.setOnClickListener {
            val title = binding.edtTitleNotes.text.toString()
            val detail = binding.edtDetailNotes.text.toString()

            if (title.isEmpty()) {
                showText(resources.getString(R.string.title_notes_empty_error))
            } else {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        val result = if (type) {
                            val notes =
                                NotesEntity(0, title, detail, DBHandler.FALSE_STATE, getDate())
                            db.saveNotes(notes)
                        } else {
                            val notes =
                                NotesEntity(id, title, detail, DBHandler.FALSE_STATE, getDate())
                            db.editNotes(notes)
                        }

                        withContext(Dispatchers.Main) {
                            if (result) {
                                showText(resources.getString(R.string.save_notes_successful))
                                finish()
                            } else {
                                showText(resources.getString(R.string.save_notes_unsuccessful))
                            }
                        }
                    }
                }
            }
        }

        binding.btnCancel.setOnClickListener { finish() }
    }

    private fun getDate(): String {
        val pdate = PersianDate()
//        Log.d("Timestamp", pdate.toString())
        return pdate.toString()
    }

    private fun showText(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}