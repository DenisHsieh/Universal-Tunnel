package io.denix.project.universaltunnel.data.note.model

import io.denix.project.universaltunnel.data.external.Note
import io.denix.project.universaltunnel.network.model.NetworkNote

fun NetworkNote.asEntity() = NoteEntity(
    id = id,
    userId = userId,
    title = title,
    content = content,
    imageUrl = imageUrl,
    backgroundColor = backgroundColor
)

fun Note.asNetworkNote() = NetworkNote(
    id = id,
    userId = userId,
    title = title,
    content = content,
    imageUrl = imageUrl,
    backgroundColor = backgroundColor
)

fun Note.asNoteEntity() = NoteEntity(
    id = id,
    userId = userId,
    title = title,
    content = content,
    imageUrl = imageUrl,
    backgroundColor = backgroundColor
)