package hr.foi.air.concertstager.ws.request_handlers.Review

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.PerformerReviewCreateBody
import hr.foi.air.concertstager.ws.models.response.PerformerReview
import hr.foi.air.concertstager.ws.network.NetworkService
import hr.foi.air.concertstager.ws.request_handlers.TemplateRequestHandler
import retrofit2.Call

class CreateReviewPerformerRequestHandler(private val jwt: String, private val performerReviewBody: PerformerReviewCreateBody):TemplateRequestHandler<PerformerReview>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<PerformerReview>> {
        val service = NetworkService.reviewService
        return service.reviewPerformer(jwt, performerReviewBody)
    }
}