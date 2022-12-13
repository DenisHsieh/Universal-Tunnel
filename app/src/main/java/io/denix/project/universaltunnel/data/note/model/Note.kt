package io.denix.project.universaltunnel.data.note.model

import io.denix.project.universaltunnel.network.model.NetworkNote

fun NetworkNote.asEntity() = NoteEntity(
    id = id,
    userId = userId,
    title = title,
    content = content,
    backgroundColor = backgroundColor
)