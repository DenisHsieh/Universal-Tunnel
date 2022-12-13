package io.denix.project.universaltunnel.data.external

/**
 * External data layer representation of an User
 */
data class User(
    val id: Int?,
    val firstName: String?,
    val lastName: String?,
    val age: Int?,
    val gender: String?,
    val height: Int?,
    val weight: Int?
)

//val previewUsers = listOf(
//    User(),
//    User(),
//)
