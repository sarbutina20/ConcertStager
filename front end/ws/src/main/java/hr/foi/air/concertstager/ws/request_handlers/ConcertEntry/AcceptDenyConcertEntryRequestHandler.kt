package hr.foi.air.concertstager.ws.request_handlers.ConcertEntry

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.ConcertEntryUpdateBody
import hr.foi.air.concertstager.ws.models.response.ConcertEntry
import hr.foi.air.concertstager.ws.network.NetworkService
import hr.foi.air.concertstager.ws.request_handlers.TemplateRequestHandler
import retrofit2.Call

class AcceptDenyConcertEntryRequestHandler(private val jwt: String, private val id: Int, private val concertEntryUpdateBody: ConcertEntryUpdateBody): TemplateRequestHandler<ConcertEntry>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<ConcertEntry>> {
        val service = NetworkService.concertService
        return service.acceptDenyEntry(jwt, id, concertEntryUpdateBody)
    }
}