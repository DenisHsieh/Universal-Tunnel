package io.denix.project.universaltunnel.network.model

import kotlinx.serialization.Serializable

/**
 * Network representation of Note
 */
@Serializable
data class NetworkNote(
    val id: Int?,
    val userId: Int?,
    val title: String?,
    val content: String?,
    val backgroundColor: String?
)