package hr.foi.air.concertstager.ws.network

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.OrganizerUpdateBody
import hr.foi.air.concertstager.ws.models.response.Organizer
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface OrganizerService {
    @PATCH("Organizer/{id}")
    fun updateOrganizer(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int,
        @Body organizerUpdateBody: OrganizerUpdateBody
    ) : Call<SuccessfulResponseBody<Organizer>>

    @GET("Organizer/{id}")
    fun getOrganizer(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int,
    ) : Call<SuccessfulResponseBody<Organizer>>
}