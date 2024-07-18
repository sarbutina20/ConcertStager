package hr.foi.air.concertstager.ws.request_handlers.User

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.response.User
import hr.foi.air.concertstager.ws.network.NetworkService
import hr.foi.air.concertstager.ws.request_handlers.TemplateRequestHandler
import retrofit2.Call

class GetAllUsersRequestHandler(private val jwt: String,) : TemplateRequestHandler<User>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<User>> {
        val service = NetworkService.performerService
        return service.getAllUsers(jwt)
    }
}