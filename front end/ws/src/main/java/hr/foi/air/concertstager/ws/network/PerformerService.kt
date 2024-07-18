package hr.foi.air.concertstager.ws.network

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.response.Performer
import hr.foi.air.concertstager.ws.models.PerformerUpdateBody
import hr.foi.air.concertstager.ws.models.response.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface PerformerService {
    @PATCH("Performer/{id}")
    fun updatePerformer(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int,
        @Body performerUpdateBody: PerformerUpdateBody
    ) : Call<SuccessfulResponseBody<Performer>>

    @GET("Performer/{id}")
    fun getPerformer(
        @Header("Authorization") authorization: String,
        @Path("id") id : Int
    ) : Call<SuccessfulResponseBody<Performer>>

    @GET("User")
    fun getAllUsers(
        @Header("Authorization") authorization: String
    ) : Call<SuccessfulResponseBody<User>>

    @GET("Performer")
    fun getPerformers(
        @Header("Authorization") authorization: String
    ) : Call<SuccessfulResponseBody<Performer>>
}