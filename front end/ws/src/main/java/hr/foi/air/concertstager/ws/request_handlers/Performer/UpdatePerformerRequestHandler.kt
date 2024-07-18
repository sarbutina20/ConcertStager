package hr.foi.air.concertstager.ws.request_handlers.Performer

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.response.Performer
import hr.foi.air.concertstager.ws.models.PerformerUpdateBody
import hr.foi.air.concertstager.ws.network.NetworkService
import hr.foi.air.concertstager.ws.request_handlers.TemplateRequestHandler
import retrofit2.Call

class UpdatePerformerProfileRequestHandler (private val jwt: String, private val userId : Int, private val body: PerformerUpdateBody) :
    TemplateRequestHandler<Performer>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<Performer>> {
        val service = NetworkService.performerService
        return service.updatePerformer(jwt, userId, body)
    }
}