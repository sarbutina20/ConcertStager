package hr.foi.air.concertstager.ws.network

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.PerformerReviewCreateBody
import hr.foi.air.concertstager.ws.models.VenueReviewCreateBody
import hr.foi.air.concertstager.ws.models.response.PerformerReview
import hr.foi.air.concertstager.ws.models.response.Review
import hr.foi.air.concertstager.ws.models.response.VenueReview
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewService {

    @POST("PerformerReview")
    fun reviewPerformer(
        @Header("Authorization") authorization: String,
        @Body performerReviewBody: PerformerReviewCreateBody
    ) : Call<SuccessfulResponseBody<PerformerReview>>

    @GET("PerformerReview/{id}")
    fun getPerformerReviews(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int,
    ) : Call<SuccessfulResponseBody<PerformerReview>>

    @POST("VenueReview")
    fun reviewVenue(
        @Header("Authorization") authorization: String,
        @Body venueReviewBody: VenueReviewCreateBody
    ) : Call<SuccessfulResponseBody<VenueReview>>

    @GET("VenueReview/{id}")
    fun getVenueReviews(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int,
    ) : Call<SuccessfulResponseBody<VenueReview>>

    @GET("PerformerReview/user/{id}")
    fun getReviewsForUser(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int,
    ) : Call<SuccessfulResponseBody<Review>>
}