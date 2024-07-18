package hr.foi.air.concertstager.ws.models

data class PerformerReviewCreateBody(
    val grade: Int? = null,
    val description: String? = null,
    val userReviewId: Int? = null,
    val userId: Int? = null
)