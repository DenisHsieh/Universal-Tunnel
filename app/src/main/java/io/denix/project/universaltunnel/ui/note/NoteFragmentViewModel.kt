package io.denix.project.universaltunnel.ui.note

import android.app.Application
import android.content.res.AssetManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.denix.project.universaltunnel.data.external.Note
import io.denix.project.universaltunnel.data.login.LoginDao
import io.denix.project.universaltunnel.data.login.LoginRepository
import io.denix.project.universaltunnel.data.note.model.NoteDao
import io.denix.project.universaltunnel.data.note.repository.FakeNetworkNoteRepository
import io.denix.project.universaltunnel.data.note.repository.NoteRepository
import io.denix.project.universaltunnel.network.fake.FakeNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class NoteFragmentViewModel(
    application: Application,
    private val noteDao: NoteDao,
    private val loginDao: LoginDao,
    private val assetManager: AssetManager
) : AndroidViewModel(application) {

    private var noteRepository: NoteRepository
    private var loginRepository: LoginRepository
    private val ioDispatcher = Dispatchers.IO

    init {
        val fakeNetworkDataSource = FakeNetworkDataSource(ioDispatcher, Json, assetManager)
        noteRepository = FakeNetworkNoteRepository(noteDao, ioDispatcher, fakeNetworkDataSource)
        loginRepository = LoginRepository(loginDao)

        fetchAllNotes()
    }

    private fun fetchAllNotes() {
        viewModelScope.launch(ioDispatcher) {
            if (noteDao.getNoteEntities().isEmpty()) {
                noteRepository.syncInDatabase()
            }

//            檢查使否有從dataSource，取得Note資料
            noteRepository.getNotes().collect { noteList ->
                noteList.map { note ->
                    Log.d("noteRepository", note.toString())
                }
            }
//            檢查資料庫中，是否有Note資料
            noteDao.getNoteEntitiesFlow().collect { noteEntityList ->
                noteEntityList.map { noteEntity ->
                    Log.d("noteEntity", noteEntity.toString())
                }
            }
        }
    }

    suspend fun getNotesByUser(userId: Int): List<Note> {
        val noteList = viewModelScope.async(ioDispatcher) {
            return@async (noteRepository as FakeNetworkNoteRepository).getNotesByUser(userId)
        }
        return noteList.await()
    }
}