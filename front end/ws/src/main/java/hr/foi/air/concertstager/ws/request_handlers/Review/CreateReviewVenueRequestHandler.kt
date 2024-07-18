package hr.foi.air.concertstager.ws.request_handlers.Review

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.VenueReviewCreateBody
import hr.foi.air.concertstager.ws.models.response.VenueReview
import hr.foi.air.concertstager.ws.network.NetworkService
import hr.foi.air.concertstager.ws.request_handlers.TemplateRequestHandler
import retrofit2.Call

class CreateReviewVenueRequestHandler(private val jwt: String, private val venueReviewCreateBody: VenueReviewCreateBody): TemplateRequestHandler<VenueReview>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<VenueReview>> {
        val service = NetworkService.reviewService
        return service.reviewVenue(jwt, venueReviewCreateBody)
    }
}