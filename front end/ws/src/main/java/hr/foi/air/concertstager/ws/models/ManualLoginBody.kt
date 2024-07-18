package hr.foi.air.concertstager.ws.models

data class ManualLoginBody (
    val loginType: Int = 1,
    val email: String?,
    val password: String?,
)

