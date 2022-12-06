package io.denix.project.universaltunnel.data.note

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg note: Note)

    @Delete
    fun delete(vararg note: Note)

    @Query("DELETE FROM note_table")
    fun deleteAll()

    @Update
    fun updateNote(vararg note: Note)

    @Query("SELECT * FROM note_table")
    fun getNotes(): Flow<List<Note>>
}