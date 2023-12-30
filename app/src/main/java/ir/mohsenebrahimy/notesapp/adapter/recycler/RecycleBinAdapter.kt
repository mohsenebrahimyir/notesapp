package ir.mohsenebrahimy.notesapp.adapter.recycler

import android.app.AlertDialog
import android.content.Context
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ir.mohsenebrahimy.notesapp.R
import ir.mohsenebrahimy.notesapp.data.local.db.DBHelper
import ir.mohsenebrahimy.notesapp.data.local.db.dao.NotesDao
import ir.mohsenebrahimy.notesapp.data.model.RecyclerNotesModel
import ir.mohsenebrahimy.notesapp.databinding.ListItemRecycleBinBinding

class RecycleBinAdapter(
    private val context: Context,
    private val dao: NotesDao
) : RecyclerView.Adapter<RecycleBinAdapter.RecycleViewHolder>() {
    private val allData = dao.getNotesForRecycler(DBHelper.TRUE_STATE)

    override fun getItemCount(): Int = allData.size

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        holder.setData(allData[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecycleViewHolder(
        ListItemRecycleBinBinding.inflate(LayoutInflater.from(context), parent, false)
    )

    inner class RecycleViewHolder(
        private val binding: ListItemRecycleBinBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setData(data: RecyclerNotesModel) {
            binding.txtTitleNotes.text = data.title

            binding.imgDeleteNotes.setOnClickListener {
                AlertDialog.Builder(ContextThemeWrapper(context, R.style.CustomAlertDialog))
                    .setTitle(context.getString(R.string.remove_note))
                    .setMessage(context.getString(R.string.do_you_want_to_delete_the_note_permanently))
                    .setIcon(R.drawable.ic_delete_permanently)
                    .setNegativeButton(context.getString(R.string.yes)) { _, _ ->
                        val result = dao.deleteNotes(data.id)
                        if (result) {
                            showText(context.getString(R.string.remove_note_permanently))
                            allData.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                        } else {
                            showText(context.getString(R.string.the_operation_encountered_a_problem))
                        }
                    }
                    .setPositiveButton(context.getString(R.string.no)) { _, _ -> }
                    .create()
                    .show()
            }

            binding.imgRestoreNotes.setOnClickListener {
                AlertDialog.Builder(ContextThemeWrapper(context, R.style.CustomAlertDialog))
                    .setTitle(context.getString(R.string.restore_the_note))
                    .setMessage(context.getString(R.string.do_you_want_the_note_to_be_moved_the_notes_pages))
                    .setIcon(R.drawable.ic_delete_permanently)
                    .setNegativeButton(context.getString(R.string.yes)) { _, _ ->
                        val result = dao.editNotes(data.id, DBHelper.FALSE_STATE)
                        if (result) {
                            showText(context.getString(R.string.restore_the_note))
                            allData.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                        } else {
                            showText(context.getString(R.string.the_operation_encountered_a_problem))
                        }
                    }
                    .setPositiveButton(context.getString(R.string.no)) { _, _ -> }
                    .create()
                    .show()
            }

        }
    }

    private fun showText(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}