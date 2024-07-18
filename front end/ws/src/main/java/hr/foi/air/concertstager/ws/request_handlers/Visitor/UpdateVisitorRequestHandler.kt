package hr.foi.air.concertstager.ws.request_handlers.Visitor

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.VisitorUpdateBody
import hr.foi.air.concertstager.ws.models.response.Visitor
import hr.foi.air.concertstager.ws.network.NetworkService
import hr.foi.air.concertstager.ws.request_handlers.TemplateRequestHandler
import retrofit2.Call

class UpdateVisitorRequestHandler(private val jwt: String, private val id: Int, private val visitorUpdateBody: VisitorUpdateBody)
    : TemplateRequestHandler<Visitor>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<Visitor>> {
        val service = NetworkService.visitorService
        return service.updateVisitor(jwt, id, visitorUpdateBody)
    }
}