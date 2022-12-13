package io.denix.project.universaltunnel.data.note.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query(value = "SELECT * FROM note_table WHERE id = :noteId")
    fun getNoteEntity(noteId: Int): Flow<NoteEntity>

    @Query(value = "SELECT * FROM note_table")
    fun getNoteEntities(): Flow<List<NoteEntity>>

    @Query(value = "SELECT * FROM note_table WHERE id IN (:noteIds)")
    fun getNoteEntities(noteIds: Set<Int>): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreNotes(noteEntities: List<NoteEntity>): List<Long>

    /**
     * Updates [entities] in the db that match the primary key, and no-ops if they don't
     */
    @Update
    suspend fun updateNotes(entities: List<NoteEntity>)

    /**
     * Inserts or updates [entities] in the db under the specified primary keys
     */
    @Upsert
    suspend fun upsertNotes(entities: List<NoteEntity>)

    @Query(value = "DELETE FROM note_table WHERE id in (:noteIds)")
    suspend fun deleteNotes(noteIds: List<Int>)

    @Query(value = "DELETE FROM note_table")
    suspend fun deleteAllNotes()
}