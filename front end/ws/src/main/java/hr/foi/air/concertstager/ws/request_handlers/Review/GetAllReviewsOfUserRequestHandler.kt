package hr.foi.air.concertstager.ws.request_handlers.Review

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.response.Review
import hr.foi.air.concertstager.ws.network.NetworkService
import hr.foi.air.concertstager.ws.request_handlers.TemplateRequestHandler
import retrofit2.Call

class GetAllReviewsOfUserRequestHandler(private val jwt: String, private val id: Int): TemplateRequestHandler<Review>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<Review>> {
        val service = NetworkService.reviewService
        return service.getReviewsForUser(jwt, id)
    }
}