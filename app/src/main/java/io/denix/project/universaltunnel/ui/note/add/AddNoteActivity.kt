package io.denix.project.universaltunnel.ui.note.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import io.denix.project.universaltunnel.R
import io.denix.project.universaltunnel.common.SharedPrefsUtil
import io.denix.project.universaltunnel.common.UtApplication
import io.denix.project.universaltunnel.common.UtRoomDatabase
import io.denix.project.universaltunnel.data.external.Note
import io.denix.project.universaltunnel.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var viewModel: AddNoteViewModel
    private lateinit var utRoomDatabase: UtRoomDatabase

    private lateinit var note: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeUi()
        initializeViewModel()

        val sharedPrefsUtil = SharedPrefsUtil()
        val userId = sharedPrefsUtil.getLoginUserId(this)
        note = Note(id = 0,
                    userId = userId,
                    title = "",
                    content = "",
                    imageUrl = "",
                    backgroundColor = "")

        this.onBackPressedDispatcher.addCallback(this) {
            addNote()
            delayedFinish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                addNote()
                delayedFinish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeUi() {
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initializeViewModel() {
        utRoomDatabase = (application as UtApplication).database
        val userDao = utRoomDatabase.userDao()
        val noteDao = utRoomDatabase.noteDao()

        viewModel = ViewModelProvider(this,
            AddNoteViewModelFactory(application, userDao, noteDao, this.assets)
        )[AddNoteViewModel::class.java]
    }

    private fun addNote() {
        val title = binding.textInputEditTextTitle.text.toString()
        val content = binding.textInputEditTextContent.text.toString()

        if (title.isNullOrEmpty() && content.isNullOrEmpty()) {
            Snackbar.make(binding.root, getString(R.string.discard_empty_note), Snackbar.LENGTH_LONG).show()
        } else {
            note.id = System.currentTimeMillis().toInt()
            note.title = title
            note.content = content

            viewModel.addSingleNote(note)
        }
    }

    private fun delayedFinish() {
        val handler = Handler(Looper.myLooper()!!)
        val finishRunnable = Runnable { finish() }
        handler.postDelayed(finishRunnable, 1500)
    }
}