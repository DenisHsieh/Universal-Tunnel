package io.denix.project.universaltunnel.data.external

/**
 * External data layer representation of a Note
 */
data class Note(
    val id: Int?,
    val userId: Int?,
    val title: String?,
    val content: String?,
    val backgroundColor: String?
)

//val previewNote = listOf(
//    Note(),
//    Note()
//)
