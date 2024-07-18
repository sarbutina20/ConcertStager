package hr.foi.air.concertstager.ws.request_handlers.Genre

import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.Genre
import hr.foi.air.concertstager.ws.network.NetworkService
import hr.foi.air.concertstager.ws.request_handlers.TemplateRequestHandler
import retrofit2.Call

class FetchGenreRequestHandler: TemplateRequestHandler<Genre>() {
    override fun getServiceCall(): Call<SuccessfulResponseBody<Genre>> {
        val service = NetworkService.genreService
        return service.getAllGenres()
    }
}