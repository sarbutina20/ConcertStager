package hr.foi.air.concertstager.ws.request_handlers.Venue

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.response.Venue
import hr.foi.air.concertstager.ws.network.NetworkService
import hr.foi.air.concertstager.ws.request_handlers.TemplateRequestHandler
import retrofit2.Call

class GetVenuesRequestHandler(private val jwt: String) : TemplateRequestHandler<Venue>(){
    override fun getServiceCall(): Call<SuccessfulResponseBody<Venue>> {
        val service = NetworkService.venueService
        return service.getVenues(jwt)
    }
}