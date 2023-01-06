package io.denix.project.universaltunnel.data.note.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.denix.project.universaltunnel.data.external.Note

@Entity(tableName = "note_table")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "userId") val userId: Int?,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "content") var content: String?,
    @ColumnInfo(name = "imageUrl") var imageUrl: String?,
    @ColumnInfo(name = "backgroundColor") var backgroundColor: String?
)

fun NoteEntity.asExternalModel() = Note(
    id = id,
    userId = userId,
    title = title,
    content = content,
    imageUrl = imageUrl,
    backgroundColor = backgroundColor
)
