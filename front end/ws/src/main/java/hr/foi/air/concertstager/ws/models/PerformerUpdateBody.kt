package hr.foi.air.concertstager.ws.models

data class PerformerUpdateBody (
    var genreId: Int? = null,
    var name: String? = null,
    var email: String? = null
)