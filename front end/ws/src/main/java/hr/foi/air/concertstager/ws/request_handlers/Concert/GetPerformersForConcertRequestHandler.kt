package hr.foi.air.concertstager.ws.request_handlers.Concert

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.response.Performer
import hr.foi.air.concertstager.ws.network.NetworkService
import hr.foi.air.concertstager.ws.request_handlers.TemplateRequestHandler
import retrofit2.Call

class GetPerformersForConcertRequestHandler(private val jwt: String, private val concertId: Int): TemplateRequestHandler<Performer>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<Performer>> {
        val service = NetworkService.concertService
        return service.getPerformersForConcert(jwt, concertId)
    }
}