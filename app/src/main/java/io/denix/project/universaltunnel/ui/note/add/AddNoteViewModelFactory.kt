package io.denix.project.universaltunnel.ui.note.add

import android.app.Application
import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.denix.project.universaltunnel.data.note.model.NoteDao
import io.denix.project.universaltunnel.data.user.model.UserDao

class AddNoteViewModelFactory(
    val application: Application,
    val userDao: UserDao,
    val noteDao: NoteDao,
    private val assetManager: AssetManager
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddNoteViewModel(application, userDao, noteDao, assetManager) as T
    }
}