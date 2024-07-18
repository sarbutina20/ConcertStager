package hr.foi.air.concertstager.ws.request_handlers.Organizer

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.response.Organizer
import hr.foi.air.concertstager.ws.network.NetworkService
import hr.foi.air.concertstager.ws.request_handlers.TemplateRequestHandler
import retrofit2.Call

class GetOrganizerRequestHandler (private val jwt: String, private val id: Int):
    TemplateRequestHandler<Organizer>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<Organizer>> {
        val service = NetworkService.organizerService
        return service.getOrganizer(jwt, id)
    }
}