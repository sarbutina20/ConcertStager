package hr.foi.air.concertstager.ws.request_handlers.Concert

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.response.Concert
import hr.foi.air.concertstager.ws.network.NetworkService
import hr.foi.air.concertstager.ws.request_handlers.TemplateRequestHandler
import retrofit2.Call

class GetOrganizerConcertsRequestHandler(private val jwt: String, private val id: Int): TemplateRequestHandler<Concert>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<Concert>> {
        val service = NetworkService.concertService
        return  service.getOrganizerConcerts(jwt, id)
    }
}