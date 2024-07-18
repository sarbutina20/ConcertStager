package hr.foi.air.concertstager.ws.models

data class CreateConcertEntryBody(
    var userId: Int? = null,
    var concertId: Int? = null
)
