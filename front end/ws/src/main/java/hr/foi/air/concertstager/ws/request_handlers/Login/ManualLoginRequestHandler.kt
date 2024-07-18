package hr.foi.air.concertstager.ws.request_handlers.Login

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.ManualLoginBody
import hr.foi.air.concertstager.ws.models.RegisteredUser
import hr.foi.air.concertstager.ws.network.NetworkService
import hr.foi.air.concertstager.ws.request_handlers.TemplateRequestHandler
import retrofit2.Call

class ManualLoginRequestHandler (private val requestBody:ManualLoginBody) : TemplateRequestHandler<RegisteredUser>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<RegisteredUser>> {
        val service = NetworkService.authService
        return service.loginUser(requestBody)
    }
}