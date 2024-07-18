package hr.foi.air.concertstager.ws.models

data class VenueUpdateBody(
    var name: String? = null,
    var description: String? = null,
    var city: String? = null,
    var address: String? = null,
    var capacity: Int? = null
)
