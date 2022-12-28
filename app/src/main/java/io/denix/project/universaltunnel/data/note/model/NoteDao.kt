package io.denix.project.universaltunnel.data.note.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    /**
     * with Flow
     */
    @Query(value = "SELECT * FROM note_table WHERE id = :noteId")
    fun getNoteEntityFlow(noteId: Int): Flow<NoteEntity>

    /**
     * with Flow
     */
    @Query(value = "SELECT * FROM note_table")
    fun getNoteEntitiesFlow(): Flow<List<NoteEntity>>

    /**
     * with Flow
     */
    @Query(value = "SELECT * FROM note_table WHERE id IN (:noteIds)")
    fun getNoteEntitiesFlow(noteIds: Set<Int>): Flow<List<NoteEntity>>

    /**
     * with Flow
     */
    @Query(value = "SELECT * FROM note_table WHERE userId = :userId")
    fun getNoteEntitiesByUserFlow(userId: Int): Flow<List<NoteEntity>>

    @Query(value = "SELECT * FROM note_table WHERE id = :noteId")
    fun getNoteEntity(noteId: Int): NoteEntity

    @Query(value = "SELECT * FROM note_table")
    fun getNoteEntities(): List<NoteEntity>

    @Query(value = "SELECT * FROM note_table WHERE id IN (:noteIds)")
    fun getNoteEntities(noteIds: Set<Int>): List<NoteEntity>

    @Query(value = "SELECT * FROM note_table WHERE userId = :userId")
    fun getNoteEntitiesByUser(userId: Int): List<NoteEntity>

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