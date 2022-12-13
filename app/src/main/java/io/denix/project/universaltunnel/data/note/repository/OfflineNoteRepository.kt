package io.denix.project.universaltunnel.data.note.repository

import io.denix.project.universaltunnel.data.external.Note
import io.denix.project.universaltunnel.data.note.model.NoteDao
import io.denix.project.universaltunnel.data.note.model.NoteEntity
import io.denix.project.universaltunnel.data.note.model.asEntity
import io.denix.project.universaltunnel.data.note.model.asExternalModel
import io.denix.project.universaltunnel.network.NetworkDataSource
import io.denix.project.universaltunnel.network.model.NetworkNote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineNoteRepository(
    private val noteDao: NoteDao,
    private val network: NetworkDataSource
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNoteEntities().map {
            it.map(NoteEntity::asExternalModel)
        }
    }

    override fun getNote(id: Int): Flow<Note> {
        return noteDao.getNoteEntity(id).map {
            it.asExternalModel()
        }
    }

    override suspend fun sync() {
        noteDao.deleteAllNotes()
        val networkNotes = network.getNotes()
        noteDao.upsertNotes(
            entities = networkNotes.map(NetworkNote::asEntity)
        )
    }
}