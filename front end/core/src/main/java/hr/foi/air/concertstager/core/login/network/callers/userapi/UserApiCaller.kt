package hr.foi.air.concertstager.core.login.network.callers.userapi

import android.os.Handler
import android.os.Looper
import android.util.Log
import hr.foi.air.concertstager.core.login.LoginUserData
import hr.foi.air.concertstager.core.login.network.NetworkServiceUrl
import hr.foi.air.concertstager.core.login.network.OkHttpClientWithSSL
import hr.foi.air.concertstager.core.login.network.callers.userapi.callbacks.ILoginUserDataCallback
import hr.foi.air.concertstager.core.login.network.parsers.parseUserFromUserLoginJSON
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class UserApiCaller(){
    private val client = OkHttpClientWithSSL.getOkHttpClientWithSSLClearance()
    private val url_register = NetworkServiceUrl.BASE_URL + "User/register";
    private val url_login = NetworkServiceUrl.BASE_URL + "User/login"

    fun registerUser(loginUserData: LoginUserData, callback: (Boolean) -> Unit) {
        Log.i("registerUser","Sending POST request")
        val jsonMediaType = "application/json; charset=utf-8".toMediaType()

        val requestBody = RequestBody.create(jsonMediaType, requestUserDataToJsonString(loginUserData))
        val request = Request.Builder()
            .url(url_register)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call, response: Response) {
                // Handle successful response
                if (response.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                callback(false)
            }
        })
    }

    fun getUserFromDatabase(google_id: String?, callback : ILoginUserDataCallback) {
        Log.i("getUserFromDatabase","Sending POST request")
        val jsonData = """{
            "loginType": 0,
            "idToken": "${google_id}",
            "email":"string",
            "password":"string"
        }"""
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonData.toRequestBody(mediaType)

        val request = Request.Builder()
            .url(url_login)
            .post(requestBody)
            .build()

        var userData : LoginUserData? = null
        Log.i("Before client.newcall","Here is the newcall")
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    userData = responseBody?.let { parseUserFromUserLoginJSON(it) }
                    if (userData != null) {
                        Handler(Looper.getMainLooper()).post {
                            callback.onUserReceived(userData)
                        }
                    } else {
                        Handler(Looper.getMainLooper()).post {
                            callback.onUserReceived(null)
                        }
                    }
                } else {
                    Handler(Looper.getMainLooper()).post {
                        callback.onUserReceived(null)
                    }
                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Handler(Looper.getMainLooper()).post {
                    callback.onUserReceived(null)
                }
            }
        })
    }
}

fun requestUserDataToJsonString(userData: LoginUserData): String {
    return """{
        "googleId": "${userData.user_google_id}",
        "name": "${userData.user_name}",
        "email": "${userData.user_email}",
        "password": "",
        "roleId": ${userData.user_roleId},
        "contactNumber": "${userData.user_contact_number}",
        "genreId": ${userData.user_genreId}
    }"""
}