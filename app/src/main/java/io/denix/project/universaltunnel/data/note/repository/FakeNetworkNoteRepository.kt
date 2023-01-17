package io.denix.project.universaltunnel.data.note.repository

import io.denix.project.universaltunnel.data.external.Note
import io.denix.project.universaltunnel.data.note.model.NoteDao
import io.denix.project.universaltunnel.data.note.model.asEntity
import io.denix.project.universaltunnel.data.note.model.asExternalModel
import io.denix.project.universaltunnel.data.note.model.asNoteEntity
import io.denix.project.universaltunnel.network.fake.FakeNetworkDataSource
import io.denix.project.universaltunnel.network.model.NetworkNote
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

class FakeNetworkNoteRepository(
    private val noteDao: NoteDao,
    private val ioDispatcher: CoroutineDispatcher,
    private val dataSource: FakeNetworkDataSource
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> = flow {
        emit(
            dataSource.getNotes().map {
                Note(
                    id = it.id,
                    userId = it.userId,
                    title = it.title,
                    content = it.content,
                    imageUrl = it.imageUrl,
                    backgroundColor = it.backgroundColor
                )
            }
        )
    }.flowOn(ioDispatcher)

    override fun getNote(noteId: Int): Flow<Note> = flow {
        emit(
            noteDao.getNoteEntity(noteId).asExternalModel()
        )
    }.flowOn(ioDispatcher)

    fun getSingleNote(noteId: Int): Note {
        return noteDao.getNoteEntity(noteId).asExternalModel()
    }

    fun getNotesByUser(userId: Int): List<Note> {
        return noteDao.getNoteEntitiesByUser(userId).map {
            it.asExternalModel()
        }
    }

    suspend fun updateSingleNote(note: Note) {
        val noteEntityList = listOf(note.asNoteEntity())
        noteDao.updateNotes(noteEntityList)
    }

    suspend fun addSingleNote(note: Note) {
        val noteEntityList = listOf(note.asNoteEntity())
        noteDao.insertOrIgnoreNotes(noteEntityList)
    }

    override suspend fun syncInDatabase() {
        val networkNotes = dataSource.getNotes()
        noteDao.deleteAllNotes()
        noteDao.upsertNotes(
            entities = networkNotes.map(NetworkNote::asEntity)
        )
    }
}