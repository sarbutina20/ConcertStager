package hr.foi.air.concertstager.core.login.network.parsers

interface IJSONparser {
    fun parseJSON(responseBody : String)
}