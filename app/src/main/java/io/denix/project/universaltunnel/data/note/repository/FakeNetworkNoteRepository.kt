package io.denix.project.universaltunnel.data.note.repository

import io.denix.project.universaltunnel.data.external.Note
import io.denix.project.universaltunnel.data.note.model.NoteDao
import io.denix.project.universaltunnel.data.note.model.asEntity
import io.denix.project.universaltunnel.data.note.model.asExternalModel
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
                    backgroundColor = it.backgroundColor
                )
            }
        )
    }.flowOn(ioDispatcher)

    override fun getNote(id: Int): Flow<Note> {
        return getNotes().map { it.first { note -> note.id == id } }
    }

    fun getNotesByUser(userId: Int): List<Note> {
        return noteDao.getNoteEntitiesByUser(userId).map {
            it.asExternalModel()
        }
    }

    override suspend fun syncInDatabase() {
        val networkNotes = dataSource.getNotes()
        noteDao.deleteAllNotes()
        noteDao.upsertNotes(
            entities = networkNotes.map(NetworkNote::asEntity)
        )
    }
}