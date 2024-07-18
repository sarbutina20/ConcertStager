package hr.foi.air.concertstager.core.login

interface LoginOutcomeListener {
    fun onSuccessfulLogin(loginUserData: LoginUserData)
    fun onFailedLogin(reason: String)
}