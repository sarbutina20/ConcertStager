package hr.foi.air.concertstager.ws.network

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.VisitorUpdateBody
import hr.foi.air.concertstager.ws.models.response.Visitor
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface VisitorService {

    @PATCH("Visitor/{id}")
    fun updateVisitor(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int,
        @Body visitorUpdateBody: VisitorUpdateBody
    ) : Call<SuccessfulResponseBody<Visitor>>

    @GET("Visitor/{id}")
    fun getVisitor(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ) : Call<SuccessfulResponseBody<Visitor>>
}