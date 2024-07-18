package hr.foi.air.concertstager.ws.models
data class RegisteredUser(
    val id : Int,
    val googleId : String?,
    val name : String?,
    val email : String?,
    val password : String?,
    var roleId : Int?,
    val jwt : String?,
    val genreId : Int?,
    val contactNumber : String?
)
