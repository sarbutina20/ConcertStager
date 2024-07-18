package hr.foi.air.concertstager.ws.request_handlers.Concert

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.response.Concert
import hr.foi.air.concertstager.ws.network.NetworkService
import hr.foi.air.concertstager.ws.request_handlers.TemplateRequestHandler
import retrofit2.Call

class GetUpcomingConcertsRequestHandler(private val jwt: String) : TemplateRequestHandler<Concert>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<Concert>> {
        val service = NetworkService.concertService
        return service.getUpcomingConcerts(jwt)
    }
}