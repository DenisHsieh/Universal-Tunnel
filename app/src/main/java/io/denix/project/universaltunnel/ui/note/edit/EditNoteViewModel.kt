package io.denix.project.universaltunnel.ui.note.edit

import android.app.Application
import android.content.res.AssetManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.denix.project.universaltunnel.data.external.Note
import io.denix.project.universaltunnel.data.note.model.NoteDao
import io.denix.project.universaltunnel.data.note.repository.FakeNetworkNoteRepository
import io.denix.project.universaltunnel.data.note.repository.NoteRepository
import io.denix.project.universaltunnel.network.fake.FakeNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class EditNoteViewModel(
    application: Application,
    private val noteId: Int,
    private val noteDao: NoteDao,
    private val assetManager: AssetManager
) : AndroidViewModel(application) {

    private var noteRepository: NoteRepository
    private val ioDispatcher = Dispatchers.IO

    lateinit var noteTitle: String
    lateinit var noteContent: String

    init {
        val fakeNetworkDataSource = FakeNetworkDataSource(ioDispatcher, Json, assetManager)
        noteRepository = FakeNetworkNoteRepository(noteDao, ioDispatcher, fakeNetworkDataSource)

        viewModelScope.launch(ioDispatcher) {
            val note = getSingleNote(noteId)
            noteTitle = note.title.toString()
            noteContent = note.content.toString()
        }
    }

    suspend fun getSingleNote(noteId: Int): Note {
        val note = viewModelScope.async(ioDispatcher) {
            return@async (noteRepository as FakeNetworkNoteRepository).getSingleNote(noteId)
        }
        return note.await()
    }

    fun updateSingleNote(note: Note) {
        viewModelScope.launch(ioDispatcher) {
            (noteRepository as FakeNetworkNoteRepository).updateSingleNote(note)
        }
    }
}