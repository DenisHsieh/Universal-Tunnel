package io.denix.project.universaltunnel.ui.note.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import io.denix.project.universaltunnel.R
import io.denix.project.universaltunnel.common.UtApplication
import io.denix.project.universaltunnel.common.UtRoomDatabase
import io.denix.project.universaltunnel.data.external.Note
import io.denix.project.universaltunnel.data.external.imageList
import io.denix.project.universaltunnel.databinding.ActivityEditNoteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoteBinding
    private lateinit var viewModel: EditNoteViewModel
    private lateinit var utRoomDatabase: UtRoomDatabase
    private lateinit var glide: RequestManager

    private var noteId: Int = 0
    private lateinit var note: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        noteId = intent.getIntExtra("noteId", 0)
        initializeViewModel()
        initializeUi()

        this.onBackPressedDispatcher.addCallback(this) {
            updateNote()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        glide = Glide.with(this)
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                note = viewModel.getSingleNote(noteId)
            }

            withContext(Dispatchers.Main) {
                if (note.imageUrl == null || note.imageUrl == "") {
                    note.imageUrl = imageList.random().url
                }

                glide.load(note.imageUrl)
                     .placeholder(R.drawable.fahmi_fakhrudin_nzyzausbv0m_unsplash)
                     .diskCacheStrategy(DiskCacheStrategy.ALL)
                     .into(binding.imageViewNoteImage)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                updateNote()
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_note)
        binding.viewModel = viewModel

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initializeViewModel() {
        utRoomDatabase = (application as UtApplication).database
        val noteDao = utRoomDatabase.noteDao()

        viewModel = ViewModelProvider(this,
            EditNoteViewModelFactory(application, noteId, noteDao, this.assets)
        )[EditNoteViewModel::class.java]
    }

    private fun updateNote() {
        note.title = binding.textInputEditTextTitle.text.toString()
        note.content = binding.textInputEditTextContent.text.toString()

        viewModel.updateSingleNote(note)
    }
}