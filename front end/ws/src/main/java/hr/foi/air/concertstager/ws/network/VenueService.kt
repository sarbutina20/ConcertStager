package hr.foi.air.concertstager.ws.network

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.VenueUpdateBody
import hr.foi.air.concertstager.ws.models.response.Venue
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface VenueService {
    @GET("Venue")
    fun getVenues(
        @Header("Authorization") authorization: String
    ) : Call<SuccessfulResponseBody<Venue>>

    @GET("Venue/{id}")
    fun getVenue(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ) : Call<SuccessfulResponseBody<Venue>>
}