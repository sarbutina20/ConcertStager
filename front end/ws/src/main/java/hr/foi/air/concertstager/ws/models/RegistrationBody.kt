package hr.foi.air.concertstager.ws.models

data class RegistrationBody(
    val googleId: String?,
    val name: String?,
    val email: String?,
    val password: String?,
    var roleId: Int,
    val genreId: Int?,
    val contactNumber: String?
)
