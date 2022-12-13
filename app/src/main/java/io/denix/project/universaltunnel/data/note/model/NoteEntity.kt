package io.denix.project.universaltunnel.data.note.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.denix.project.universaltunnel.data.external.Note
import io.denix.project.universaltunnel.data.external.User
import io.denix.project.universaltunnel.data.user.model.UserEntity

@Entity(tableName = "note_table")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "userId") val userId: Int?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "backgroundColor") val backgroundColor: String?
)

fun NoteEntity.asExternalModel() = Note(
    id = id,
    userId = userId,
    title = title,
    content = content,
    backgroundColor = backgroundColor
)
