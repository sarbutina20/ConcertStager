package hr.foi.air.concertstager.core.login.network

class ApiClient : IApiClient {
    override fun <T> GetCall(callback: (Result<T>) -> Unit, result: Result<T>) {
        TODO("Not yet implemented")
    }

    override fun <T> PostCall(callback: (Result<T>) -> Unit, result: Result<T>) {
        callback(result)
    }

    override fun <T> PutCall(callback: (Result<T>) -> Unit, result: Result<T>) {
        TODO("Not yet implemented")
    }

    override fun <T> DeleteCall(callback: (Result<Boolean>) -> Unit, result: Result<T>) {
        TODO("Not yet implemented")
    }

}