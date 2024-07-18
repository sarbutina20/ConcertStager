package hr.foi.air.concertstager.ws.network

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.ManualLoginBody
import hr.foi.air.concertstager.ws.models.RegisteredUser
import hr.foi.air.concertstager.ws.models.RegistrationBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("User/register")
    fun registerUser(@Body registerBody: RegistrationBody): Call<SuccessfulResponseBody<RegisteredUser>>

    @POST("User/login")
    fun loginUser(@Body loginBody: ManualLoginBody): Call<SuccessfulResponseBody<RegisteredUser>>
}