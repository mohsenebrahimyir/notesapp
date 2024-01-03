package ir.mohsenebrahimy.notesapp.adapter.recycler

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import ir.mohsenebrahimy.notesapp.R
import ir.mohsenebrahimy.notesapp.data.local.db.DBHandler
import ir.mohsenebrahimy.notesapp.data.local.db.dao.NotesDao
import ir.mohsenebrahimy.notesapp.data.model.RecyclerNotesModel
import ir.mohsenebrahimy.notesapp.databinding.ListItemNotesBinding
import ir.mohsenebrahimy.notesapp.ui.AddNotesActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotesAdapter(
    private val context: Context,
    private val db: NotesDao,
    private var data: LiveData<T>
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    private var allData: ArrayList<RecyclerNotesModel>

    init {
        allData = db.getNotesForRecycler(DBHandler.FALSE_STATE)
    }

    override fun getItemCount(): Int = allData.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.setData(allData[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotesViewHolder(
        ListItemNotesBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
    )

    inner class NotesViewHolder(
        private val binding: ListItemNotesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setData(data: RecyclerNotesModel) {
            binding.txtTitleNotes.text = data.title
            binding.imgDeleteNotes.setOnClickListener {
                AlertDialog.Builder(
                    ContextThemeWrapper(context, R.style.CustomAlertDialog)
                )
                    .setTitle(context.getString(R.string.remove_note))
                    .setMessage(context.getString(R.string.do_you_want_the_note_to_be_moved_the_recycle_bin))
                    .setIcon(R.drawable.ic_delete_permanently)
                    .setNegativeButton(context.getString(R.string.yes)) { _, _ ->
                        lifecycleScope.launch {
                            withContext(Dispatchers.IO) {
                                val note = db.getNoteById(data.id)
                                note.deleteState = DBHandler.TRUE_STATE
                                val result = db.editNotes(note)
                                withContext(Dispatchers.Main) {
                                    if (result) {
                                        showText(context.getString(R.string.no))
                                        allData.removeAt(adapterPosition)
                                        notifyItemRemoved(adapterPosition)
                                    } else {
                                        showText(context.getString(R.string.the_operation_encountered_a_problem))
                                    }
                                }
                            }
                        }
                    }
                    .setPositiveButton(context.getString(R.string.no)) { _, _ -> }
                    .create()
                    .show()
            }
            binding.root.setOnClickListener {
                val intent = Intent(context, AddNotesActivity::class.java)
                intent.putExtra("notesId", data.id)
                context.startActivity(intent)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeData(data: ArrayList<RecyclerNotesModel>) {
        allData = data
        notifyDataSetChanged()
    }

    private fun showText(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}

