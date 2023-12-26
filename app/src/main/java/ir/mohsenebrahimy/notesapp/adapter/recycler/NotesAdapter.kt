package ir.mohsenebrahimy.notesapp.adapter.recycler

import android.app.Activity
import android.app.AlertDialog
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ir.mohsenebrahimy.notesapp.R
import ir.mohsenebrahimy.notesapp.data.local.db.DBHelper
import ir.mohsenebrahimy.notesapp.data.local.db.dao.NotesDao
import ir.mohsenebrahimy.notesapp.data.model.RecyclerNotesModel
import ir.mohsenebrahimy.notesapp.databinding.ListItemNotesBinding

class NotesAdapter(
    private val context: Activity,
    private var allData: ArrayList<RecyclerNotesModel>,
    private val dao: NotesDao
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    override fun getItemCount(): Int = allData.size
    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.setData(allData[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotesViewHolder(
        ListItemNotesBinding.inflate(context.layoutInflater, parent, false)
    )

    inner class NotesViewHolder(
        private val binding: ListItemNotesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setData(data: RecyclerNotesModel) {
            binding.txtTitleNotes.text = data.title

            binding.imgDeleteNotesRecycler.setOnClickListener {
                AlertDialog.Builder(ContextThemeWrapper(context, R.style.CustomAlertDialog))
                    .setTitle(context.getString(R.string.remove_note))
                    .setMessage(context.getString(R.string.do_you_want_move_note_to_recycle_bin))
                    .setIcon(R.drawable.ic_delete)
                    .setNegativeButton(context.getString(R.string.yes)) { _, _ ->
                        val result = dao.editNotes(data.id, DBHelper.TRUE_STATE)
                        if (result) {
                            showText(context.getString(R.string.note_move_to_recycle_bin))
                            allData.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                        } else {
                            showText(context.getString(R.string.operation_encountered_a_problem))
                        }
                    }
                    .setPositiveButton(context.getString(R.string.no)) { _, _ -> }
                    .create()
                    .show()
            }
        }
    }

    fun changeData(data: ArrayList<RecyclerNotesModel>) {
        if (data.size > allData.size) {
            allData = data
            notifyItemInserted(allData.size)
        }
    }

    private fun showText(text:String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}