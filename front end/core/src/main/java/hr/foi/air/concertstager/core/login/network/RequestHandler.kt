package hr.foi.air.concertstager.core.login.network

interface RequestHandler<T> {
    fun sendRequest(responseListener: ResponseListener<T>)
}