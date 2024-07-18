package hr.foi.air.concertstager.ws.models

data class VenueReviewCreateBody(
    var grade: Int? = null,
    var description: String? = null,
    var userReviewId: Int? = null,
    var venueId: Int? = null,
)
