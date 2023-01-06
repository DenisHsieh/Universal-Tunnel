package io.denix.project.universaltunnel.ui.note.edit

import android.app.Application
import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.denix.project.universaltunnel.data.note.model.NoteDao

class EditNoteViewModelFactory(
    val application: Application,
    private val noteId: Int,
    val noteDao: NoteDao,
    private val assetManager: AssetManager
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditNoteViewModel(application, noteId, noteDao, assetManager) as T
    }
}