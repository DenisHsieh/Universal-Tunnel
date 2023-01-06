package io.denix.project.universaltunnel.ui.note

import android.app.Application
import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.denix.project.universaltunnel.data.login.LoginDao
import io.denix.project.universaltunnel.data.note.model.NoteDao

class NoteFragmentViewModelFactory(
    val application: Application,
    val noteDao: NoteDao,
    val loginDao: LoginDao,
    private val assetManager: AssetManager
    ) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteFragmentViewModel(application, noteDao, loginDao, assetManager) as T
    }
}