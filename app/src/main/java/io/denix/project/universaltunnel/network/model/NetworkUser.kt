package io.denix.project.universaltunnel.network.model

import kotlinx.serialization.Serializable

/**
 * Network representation of User
 */
@Serializable
data class NetworkUser(
    val id: Int?,
    val firstName: String?,
    val lastName: String?,
    val age: Int?,
    val gender: String?,
    val height: Int?,
    val weight: Int?
)
