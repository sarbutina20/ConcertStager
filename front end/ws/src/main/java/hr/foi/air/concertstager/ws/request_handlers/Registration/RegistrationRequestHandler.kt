package hr.foi.air.concertstager.ws.request_handlers.Registration


import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.RegisteredUser
import hr.foi.air.concertstager.ws.models.RegistrationBody
import hr.foi.air.concertstager.ws.network.NetworkService
import hr.foi.air.concertstager.ws.request_handlers.TemplateRequestHandler
import retrofit2.Call
class RegistrationRequestHandler(private val requestBody: RegistrationBody) :
    TemplateRequestHandler<RegisteredUser>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<RegisteredUser>> {
        val service = NetworkService.authService
        return service.registerUser(requestBody)
    }
}