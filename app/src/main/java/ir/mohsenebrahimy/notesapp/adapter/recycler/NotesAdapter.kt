package ir.mohsenebrahimy.notesapp.adapter.recycler

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.mohsenebrahimy.notesapp.data.model.RecyclerNotesModel
import ir.mohsenebrahimy.notesapp.databinding.ListItemNotesBinding

class NotesAdapter(
    private val context: Activity,
    private var allData: ArrayList<RecyclerNotesModel>
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
            binding.imgDeleteNotesRecycler.setOnClickListener {}
        }
    }

    fun changeData(data: ArrayList<RecyclerNotesModel>) {
        if (data.size > allData.size) {
            allData = data
            notifyItemInserted(allData.size)
        }
    }
}