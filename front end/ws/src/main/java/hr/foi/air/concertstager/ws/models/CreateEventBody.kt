package hr.foi.air.concertstager.ws.models

data class CreateEventBody (
    var venueId: Int? = null,
    var userId: Int? = null,
    var description: String? = null,
    var name: String? = null,
    var startDate: String? = null,
    var endDate: String? = null
)