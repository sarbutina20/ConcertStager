package hr.foi.air.concertstager.core.login.network.models

data class UserDto(
    val id: Int,
    val google_ID: String,
    val name: String,
    val email: String,
    val password: String,
    val role: String,
    val jwt : String?
)
