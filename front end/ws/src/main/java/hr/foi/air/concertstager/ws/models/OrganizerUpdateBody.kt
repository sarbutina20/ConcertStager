package hr.foi.air.concertstager.ws.models

data class OrganizerUpdateBody (
    var name: String? = null,
    var email: String? = null,
    var contactNumber: String? = null
)