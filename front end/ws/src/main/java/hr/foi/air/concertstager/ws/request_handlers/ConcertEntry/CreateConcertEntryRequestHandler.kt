package hr.foi.air.concertstager.ws.request_handlers.ConcertEntry

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.CreateConcertEntryBody
import hr.foi.air.concertstager.ws.models.response.ConcertEntry
import hr.foi.air.concertstager.ws.network.NetworkService
import hr.foi.air.concertstager.ws.request_handlers.TemplateRequestHandler
import retrofit2.Call

class CreateConcertEntryRequestHandler(private val jwt: String, private val createConcertEntryBody: CreateConcertEntryBody): TemplateRequestHandler<ConcertEntry>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<ConcertEntry>> {
        val service = NetworkService.concertService
        return service.createConcertEntry(jwt, createConcertEntryBody)
    }
}