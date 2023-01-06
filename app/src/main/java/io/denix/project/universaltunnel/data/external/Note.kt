package io.denix.project.universaltunnel.data.external

/**
 * External data layer representation of a Note
 */
data class Note(
    val id: Int?,
    val userId: Int?,
    var title: String?,
    var content: String?,
    var imageUrl: String?,
    var backgroundColor: String?
)

/**
 * Default image of a Note
 */
data class Image(
    val name: String?,
    val url: String?
)

val imageList = listOf(
    Image(name = "bike1", url = "https://images.unsplash.com/photo-1670307450170-a8975b215a17?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80"),
    Image(name = "bike2", url = "https://images.unsplash.com/photo-1507035895480-2b3156c31fc8?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80"),
    Image(name = "architecture1", url = "https://images.unsplash.com/photo-1669428260043-0c7bb2177970?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1435&q=80"),
    Image(name = "architecture2", url = "https://images.unsplash.com/photo-1603915577678-6c35919766f8?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1374&q=80"),
    Image(name = "ocean1", url = "https://images.unsplash.com/photo-1476673160081-cf065607f449?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1472&q=80"),
    Image(name = "ocean2", url = "https://images.unsplash.com/photo-1524946274118-e7680e33ccc5?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80"),
    Image(name = "cafe1", url = "https://images.unsplash.com/photo-1554118811-1e0d58224f24?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2047&q=80"),
    Image(name = "cafe2", url = "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2070&q=80")
)
