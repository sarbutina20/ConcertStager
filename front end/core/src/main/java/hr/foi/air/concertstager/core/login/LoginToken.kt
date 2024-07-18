package hr.foi.air.concertstager.core.login

interface LoginToken {
    fun getAuthorizers(): Map<String, String>
}