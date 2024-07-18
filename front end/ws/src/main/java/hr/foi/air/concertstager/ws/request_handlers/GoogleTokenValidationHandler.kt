package hr.foi.air.concertstager.ws.request_handlers

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.request.GoogleSignInTokenRequest
import hr.foi.air.concertstager.ws.models.response.GoogleSignInTokenResponse
import hr.foi.air.concertstager.ws.network.NetworkService
import retrofit2.Call

class GoogleTokenValidationHandler(private val body : GoogleSignInTokenRequest) :
    TemplateRequestHandler<GoogleSignInTokenResponse>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<GoogleSignInTokenResponse>> {
        val service = NetworkService.googleSignInService
        return service.ValidateToken(body)
    }
}