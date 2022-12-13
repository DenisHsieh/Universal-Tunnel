package io.denix.project.universaltunnel.data.note.repository

import io.denix.project.universaltunnel.data.external.Note
import io.denix.project.universaltunnel.data.util.Syncable
import kotlinx.coroutines.flow.Flow

interface NoteRepository : Syncable {
    /**
     * Gets the available notes as a stream
     */
    fun getNotes(): Flow<List<Note>>

    /**
     * Gets data for a specific note
     */
    fun getNote(id: Int): Flow<Note>
}