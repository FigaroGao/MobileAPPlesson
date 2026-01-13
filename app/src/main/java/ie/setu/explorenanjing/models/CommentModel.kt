package ie.setu.explorenanjing.models

data class CommentModel(
    var id: Long = 0L,
    var attractionId: Long = 0L,
    var userName: String = "Visitor",
    var ratingCategory: String = "Good",
    var text: String = "",
    var timestamp: Long = System.currentTimeMillis()
)