package io.denix.project.universaltunnel.ui.note.recyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.denix.project.universaltunnel.data.external.Note
import io.denix.project.universaltunnel.databinding.NoteItemBinding
import io.denix.project.universaltunnel.ui.note.edit.EditNoteActivity

class NoteAdapter(private val noteList: List<Note>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: Note) {
            binding.textViewNoteTitle.text = item.title
            binding.textViewNoteContent.text = item.content

            binding.noteItem.setOnClickListener {
                val intent = Intent(itemView.context, EditNoteActivity::class.java)
                intent.putExtra("noteId", item.id)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindData(noteList[position])
    }

    override fun getItemCount(): Int {
        return noteList.size
    }
}