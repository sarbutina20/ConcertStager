package hr.foi.air.concertstager.ws.network

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.response.Genre
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GenreService {
    @GET("Genre/{id}")
    fun getGenre(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ) : Call<SuccessfulResponseBody<Genre>>

    @GET("Genre")
    fun getAllGenres(
    ): Call<SuccessfulResponseBody<hr.foi.air.concertstager.ws.models.Genre>>
}