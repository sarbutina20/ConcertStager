package hr.foi.air.concertstager.login_google_login

import android.util.Log
import hr.foi.air.concertstager.core.login.network.NetworkServiceUrl
import hr.foi.air.concertstager.core.login.network.OkHttpClientWithSSL
import hr.foi.air.concertstager.core.login.network.parsers.parseGoogleIdTokenValidity
import hr.foi.air.concertstager.ws.network.NetworkService
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class IdTokenValidator {
    fun getTokenValidity(idToken: String?, callback: (Boolean) -> Unit) {
        if (idToken == null) return
        val client = OkHttpClientWithSSL.getOkHttpClientWithSSLClearance()
        val url = NetworkServiceUrl.BASE_URL + "OAuth/GoogleSignIn"

        val jsonData = """{"idToken": "${idToken}"}"""
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonData.toRequestBody(mediaType)

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("IdTokenValidator", "Request failed", e)
                callback(false)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        val isValid: Boolean? = responseBody?.let { parseGoogleIdTokenValidity(it) }
                        if (isValid != null) {
                            callback(isValid)
                        }
                    } else {
                        Log.e("IdTokenValidator", "Unsuccessful response. Code: ${response.code}, Body: ${response.body?.string()}")
                        callback(false)
                    }
                } catch (e: Exception) {
                    Log.e("IdTokenValidator", "Exception in onResponse", e)
                    callback(false)
                }
            }
        })
    }
 }
 /*
     val requestHandler = UpdateUserProfileRequestHandler(organizerDto.id!!, organizerDto)
                        requestHandler.sendRequest(
                            object: ResponseListener<OrganizerUpdate> {
                                override fun onSuccessfullResponse(response: SuccessfulResponseBody<OrganizerUpdate>) {
                                    Log.i("USPJEH", "Uspjesno azuriran klijent")
                                    isEditing = false
                                }

                                override fun onErrorResponse(response: ErrorResponseBody) {
                                    Log.i("NEUSPJEH", "NIJE USPJESNO AZURIRAN")
                                }

                                override fun onNetworkFailure(t: Throwable) {
                                    Log.e("UpdateUserProfile", "Network failure", t)
                                }

                            }
                        )

    fun getTokenValidity(idTokenParam: String?, callback: (Boolean) -> Unit) {
        if (idTokenParam == null) return
        val token = GoogleSignInTokenRequest(idTokenParam)
        val googleTokenValidation = GoogleTokenValidationHandler(token)
        googleTokenValidation.sendRequest(object : ResponseListener<GoogleSignInTokenResponse>{
            override fun onErrorResponse(response: ErrorResponseBody) {
                // Handle error response
                callback(false)
            }

            override fun onNetworkFailure(t: Throwable) {
                // Handle network failure
                callback(false)
            }

            override fun onSuccessfullResponse(response: SuccessfulResponseBody<GoogleSignInTokenResponse>) {
                Log.i("RESPONSE", response.toString())
                val data = response.data
                if (data != null && data.isNotEmpty()) {
                    val isTokenValid = data[0]?.googleIdTokenValidity?.equals("True", ignoreCase = true) ?: false
                    callback(isTokenValid)
                } else {
                    callback(false)
                }
            }
        })

    }

}
 */