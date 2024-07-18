package hr.foi.air.concertstager.ws.network

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.request.GoogleSignInTokenRequest
import hr.foi.air.concertstager.ws.models.response.GoogleSignInTokenResponse

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GoogleSignInService {
    @POST("OAuth/GoogleSignIn")
    fun ValidateToken(
        @Body googleSignInTokenRequest: GoogleSignInTokenRequest
    ) : Call<SuccessfulResponseBody<GoogleSignInTokenResponse>>
}