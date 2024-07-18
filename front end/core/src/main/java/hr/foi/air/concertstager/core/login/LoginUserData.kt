package hr.foi.air.concertstager.core.login

data class LoginUserData(
    var userId: Int?,
    var user_google_id: String?,
    var user_name: String?,
    var user_email: String?,
    var user_roleId: Int?,
    var user_genreId: Int?,
    var user_contact_number: String?,
    var jwt : String?
)
