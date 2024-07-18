package hr.foi.air.concertstager.ws.network

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.ConcertEntryUpdateBody
import hr.foi.air.concertstager.ws.models.CreateConcertEntryBody
import hr.foi.air.concertstager.ws.models.CreateEventBody
import hr.foi.air.concertstager.ws.models.response.Concert
import hr.foi.air.concertstager.ws.models.response.ConcertEntry
import hr.foi.air.concertstager.ws.models.response.Performer
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ConcertService {
    @GET ("Concert/visitorUpcoming")
    fun getVisitorUpcomingConcerts(
        @Header("Authorization") authorization:String
    ) : Call<SuccessfulResponseBody<Concert>>

    @GET ("Concert/Upcoming")
    fun getUpcomingConcerts(
        @Header("Authorization") authorization:String
    ) : Call<SuccessfulResponseBody<Concert>>

    @GET ("Concert/finished")
    fun getFinishedConcerts(
        @Header("Authorization") authorization: String
    ) : Call<SuccessfulResponseBody<Concert>>

    @GET ("Concert/performer/{id}/accepted")
    fun getAcceptedConcertsForPerformer(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ) : Call<SuccessfulResponseBody<Concert>>

    @GET ("Concert/performer/{id}/pending")
    fun getPendingConcertsForPerformer(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ) : Call<SuccessfulResponseBody<Concert>>

    @GET ("Concert/organizer/{id}")
    fun getOrganizerConcerts(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ) : Call<SuccessfulResponseBody<Concert>>

    @GET ("Concert/{id}")
    fun getConcert(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ) : Call<SuccessfulResponseBody<Concert>>

    @GET ("Concert/{concertId}/performer")
    fun getPerformersForConcert(
        @Header("Authorization") authorization: String,
        @Path("concertId") concertId: Int
    ) : Call<SuccessfulResponseBody<Performer>>

    @GET ("Concert/venue/{id}")
    fun getVenueConcerts(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ) : Call<SuccessfulResponseBody<Concert>>

    @POST ("Concert")
    fun createEvent(
        @Header("Authorization") authorization: String,
        @Body createEventBody: CreateEventBody
    ) : Call<SuccessfulResponseBody<Concert>>

    @POST ("Concert/entry")
    fun createConcertEntry(
        @Header("Authorization") authorization: String,
        @Body createConcertEntryBody: CreateConcertEntryBody
    ) : Call<SuccessfulResponseBody<ConcertEntry>>

    @GET ("Concert/{concertId}/entry")
    fun getConcertEntries(
        @Header("Authorization") authorization: String,
        @Path("concertId") concertId: Int
    ) : Call<SuccessfulResponseBody<ConcertEntry>>

    @PATCH("Concert/entry/{id}")
    fun acceptDenyEntry(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int,
        @Body concertEntryUpdateBody: ConcertEntryUpdateBody
    ) : Call<SuccessfulResponseBody<ConcertEntry>>

    @GET("Concert/{concertId}/Unresolvedentry")
    fun getUnresolvedEntries(
        @Header("Authorization") authorization: String,
        @Path("concertId") concertId: Int
    ) : Call<SuccessfulResponseBody<ConcertEntry>>
}