package hr.foi.air.concertstager.core.login.network

interface IApiClient {
    fun <T> GetCall(callback: (Result<T>) -> Unit, result: Result<T>)

    fun <T> PostCall(callback: (Result<T>) -> Unit, result: Result<T>)

    fun <T> PutCall(callback: (Result<T>) -> Unit, result: Result<T>)

    fun <T> DeleteCall(callback: (Result<Boolean>) -> Unit, result: Result<T>)
}