package io.denix.project.universaltunnel.ui.note.add

import android.app.Application
import android.content.res.AssetManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.denix.project.universaltunnel.data.external.Note
import io.denix.project.universaltunnel.data.note.model.NoteDao
import io.denix.project.universaltunnel.data.note.repository.FakeNetworkNoteRepository
import io.denix.project.universaltunnel.data.note.repository.NoteRepository
import io.denix.project.universaltunnel.data.user.model.UserDao
import io.denix.project.universaltunnel.data.user.repository.FakeNetworkUserRepository
import io.denix.project.universaltunnel.data.user.repository.UserRepository
import io.denix.project.universaltunnel.network.fake.FakeNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class AddNoteViewModel(
    application: Application,
    private val userDao: UserDao,
    private val noteDao: NoteDao,
    private val assetManager: AssetManager
) : AndroidViewModel(application) {

    private var userRepository: UserRepository
    private var noteRepository: NoteRepository
    private val ioDispatcher = Dispatchers.IO

    init {
        val fakeNetworkDataSource = FakeNetworkDataSource(ioDispatcher, Json, assetManager)
        noteRepository = FakeNetworkNoteRepository(noteDao, ioDispatcher, fakeNetworkDataSource)
        userRepository = FakeNetworkUserRepository(userDao, ioDispatcher, fakeNetworkDataSource)
    }

    fun addSingleNote(note: Note) {
        viewModelScope.launch(ioDispatcher) {
            (noteRepository as FakeNetworkNoteRepository).addSingleNote(note)
        }
    }
}