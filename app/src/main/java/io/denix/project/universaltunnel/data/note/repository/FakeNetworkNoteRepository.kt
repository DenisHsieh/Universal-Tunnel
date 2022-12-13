package io.denix.project.universaltunnel.data.note.repository

import io.denix.project.universaltunnel.data.external.Note
import io.denix.project.universaltunnel.network.fake.FakeNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class FakeNetworkNoteRepository(
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

    override suspend fun sync() {}
}