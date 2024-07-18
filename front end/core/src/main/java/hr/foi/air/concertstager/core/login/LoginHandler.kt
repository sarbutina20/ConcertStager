package hr.foi.air.concertstager.core.login

interface LoginHandler {
    fun handleLogin(loginToken: LoginToken?, loginListener: LoginOutcomeListener)
}