package io.denix.project.universaltunnel.network.model

import kotlinx.serialization.Serializable

/**
 * Network representation of Note
 */
@Serializable
data class NetworkNote(
    val id: Int?,
    val userId: Int?,
    var title: String?,
    var content: String?,
    var imageUrl: String?,
    var backgroundColor: String?
)