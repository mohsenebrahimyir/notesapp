package ir.mohsenebrahimy.notesapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.mohsenebrahimy.notesapp.adapter.recycler.RecycleBinAdapter
import ir.mohsenebrahimy.notesapp.data.local.db.DBHandler
import ir.mohsenebrahimy.notesapp.data.local.db.dao.NotesDao
import ir.mohsenebrahimy.notesapp.databinding.ActivityRecycleBinBinding

class RecycleBinActivity : AppCompatActivity() {
    private lateinit var db: NotesDao

    private lateinit var binding: ActivityRecycleBinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecycleBinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycler()
    }


    private fun initRecycler() {
        db = DBHandler.getDatabase(this)
        val adapter = RecycleBinAdapter(this, db)

        binding.recyclerNotes.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerNotes.adapter = adapter
    }
}