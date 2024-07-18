package hr.foi.air.concertstager.ws.request_handlers

import android.util.Log
import com.google.gson.Gson
import hr.foi.air.concertstager.core.login.network.RequestHandler
import hr.foi.air.concertstager.core.login.network.ResponseListener
import hr.foi.air.concertstager.core.login.network.models.ErrorResponseBody
import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class TemplateRequestHandler<T> : RequestHandler<T> {
    override fun sendRequest(responseListener: ResponseListener<T>) {
        Log.d("TemplateRequestHandler", "Request sent!")
        val serviceCall = getServiceCall()

        serviceCall.enqueue(object : Callback<SuccessfulResponseBody<T>> {
            override fun onResponse(
                call: Call<SuccessfulResponseBody<T>>,
                response: Response<SuccessfulResponseBody<T>>
            ){
                Log.d("TemplateRequestHandler", "Response: $response")
                if (response.isSuccessful) {
                    responseListener.onSuccessfulResponse(response.body() as SuccessfulResponseBody<T>)
                    Log.d("TemplateRequestHandler", "Successful response: $response")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.d("TemplateRequestHandler", "Error response: ${errorBody.toString()}")
                    val errorResponse = Gson().fromJson(
                        errorBody.toString(),
                        ErrorResponseBody::class.java
                    )
                    Log.d("TemplateRequestHandler", "errorResponse: $errorResponse")
                    responseListener.onErrorResponse(errorResponse)
                }
            }
            override fun onFailure(call: Call<SuccessfulResponseBody<T>>, t: Throwable) {
                responseListener.onNetworkFailure(t)
                Log.e("TemplateRequestHandler", "Error during network call", t)
            }
        })
    }
    protected abstract fun getServiceCall(): Call<SuccessfulResponseBody<T>>
}