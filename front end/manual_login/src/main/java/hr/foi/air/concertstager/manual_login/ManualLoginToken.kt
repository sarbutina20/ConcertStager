package hr.foi.air.concertstager.manual_login

import hr.foi.air.concertstager.core.login.LoginToken

class ManualLoginToken (email: String, password: String) : LoginToken {
    private val authorizers = mapOf(
        "email" to email,
        "password" to password
    )

    override fun getAuthorizers(): Map<String, String> {
        return authorizers
    }

}