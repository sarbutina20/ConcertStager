package hr.foi.air.concertstager.ws.request_handlers.Performer

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.response.Performer
import hr.foi.air.concertstager.ws.network.NetworkService
import hr.foi.air.concertstager.ws.request_handlers.TemplateRequestHandler
import retrofit2.Call

class GetPerformerRequestHandler(private val jwt: String, private val id: Int) : TemplateRequestHandler<Performer>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<Performer>> {
        val service = NetworkService.performerService
        return service.getPerformer(jwt, id)
    }
}