package hr.foi.air.concertstager.viewmodels.Performer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.network.ResponseListener
import hr.foi.air.concertstager.core.login.network.models.ErrorResponseBody
import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.response.Concert
import hr.foi.air.concertstager.ws.models.response.Genre
import hr.foi.air.concertstager.ws.models.response.Performer
import hr.foi.air.concertstager.ws.models.response.PerformerReview
import hr.foi.air.concertstager.ws.models.response.User
import hr.foi.air.concertstager.ws.models.response.Venue
import hr.foi.air.concertstager.ws.request_handlers.Concert.GetAcceptedConcertsForPerformerRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.Genre.GetGenreRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.Performer.GetPerformerRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.Review.GetPerformerReviewsRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.User.GetAllUsersRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.Venue.GetVenuesRequestHandler

class PerformerProfilePageViewModel : ViewModel() {
    var originalName: MutableLiveData<String> = MutableLiveData("")
    var originalEmail: MutableLiveData<String> = MutableLiveData("")
    var originalGenreId: MutableLiveData<Int> = MutableLiveData()

    var name:MutableLiveData<String> = MutableLiveData("")
    var email:MutableLiveData<String> = MutableLiveData("")
    var genreId:MutableLiveData<Int> = MutableLiveData()
    private val _genres: MutableLiveData<List<Genre>> = MutableLiveData(mutableListOf())
    private val _genreNames: MutableLiveData<List<String>> = MutableLiveData(mutableListOf())
    val genreNames: LiveData<List<String>> = _genreNames

    private val _performerReviews : MutableLiveData<List<PerformerReview>> = MutableLiveData(mutableListOf())
    val performerReviews : MutableLiveData<List<PerformerReview>> = _performerReviews

    private val _users : MutableLiveData<List<User>> = MutableLiveData(mutableListOf())
    val users : MutableLiveData<List<User>> = _users

    private val _performerConcerts : MutableLiveData<List<Concert>> = MutableLiveData(mutableListOf())
    val performerConcerts : MutableLiveData<List<Concert>> = _performerConcerts

    private val _venues : MutableLiveData<List<Venue>> = MutableLiveData(mutableListOf())
    val venues : MutableLiveData<List<Venue>> = _venues

    private val _errorMessage: MutableLiveData<String> = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    fun getGenre(){
        val id = genreId.value
        val requestHandler = GetGenreRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", id!!)
        requestHandler.sendRequest(
            object : ResponseListener<Genre> {
                override fun onSuccessfulResponse(response: SuccessfulResponseBody<Genre>) {
                    _genres.value = response.data.toMutableList()
                    _genreNames.value = _genres.value?.map { it.name!! } ?: emptyList()
                    Log.i("USPJEH", "Uspjesno dohvaceni zanrovi" + _genreNames.value)
                }

                override fun onErrorResponse(response: ErrorResponseBody) {
                    _errorMessage.value = response.message
                    Log.i("NEUSPJEH", response.message + " " + response.error_code)
                }

                override fun onNetworkFailure(t: Throwable) {
                    Log.i("INTERNET", "GRESKA NA NETU")
                }
            }
        )
    }

    fun getPerformer(id: Int){
        val requestHandler = GetPerformerRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", id)
        requestHandler.sendRequest(object : ResponseListener<Performer>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Performer>) {
                val performerDto = response.data[0]
                name.value = performerDto.name
                email.value = performerDto.email
                genreId.value = performerDto.genreId
                originalName.value = name.value
                originalEmail.value = email.value
                originalGenreId.value = genreId.value
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                _errorMessage.value = response.error_message
            }

            override fun onNetworkFailure(t: Throwable) {
                _errorMessage.value = "Network failure."
            }
        })
    }

    fun getPerformerConcerts(id: Int){
        val getAcceptedConcertsForPerformerRequestHandler = GetAcceptedConcertsForPerformerRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", id)
        getAcceptedConcertsForPerformerRequestHandler.sendRequest(object : ResponseListener<Concert>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Concert>) {
                Log.i("USPJEH", response.data.toString())
                _performerConcerts.value = response.data.toMutableList()
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.i("NEUSPJEH", response.message)
                _errorMessage.value = response.message
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.i("NETWORK", "Network failed....")
                _errorMessage.value = t.message
            }
        })
    }

    fun getVenues(){
        val getVenuesRequestHandler = GetVenuesRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}")
        getVenuesRequestHandler.sendRequest(object: ResponseListener<Venue>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Venue>) {
                Log.i("USPJEH", response.data[0].toString())
                _venues.value = response.data.toMutableList()
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.i("NEUSPJEH", response.message)
                _errorMessage.value = response.message
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.i("NETWORK", "Network failure....")
                _errorMessage.value = t.message
            }
        })
    }

    fun getAllUsers(){
        val getAllUsersRequestHandler = GetAllUsersRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}")
        getAllUsersRequestHandler.sendRequest(object : ResponseListener<User>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<User>) {
                Log.i("USPJEH", response.data.toString())
                _users.value = response.data.toMutableList()
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.i("NEUSPJEH", response.message)
                _errorMessage.value = response.message
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.i("NETWORK", "Network failed...")
                _errorMessage.value = t.message
            }

        })
    }

    fun getPerformerReviews(id: Int){
        val getPerformerReviewsRequestHandler = GetPerformerReviewsRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", id)
        getPerformerReviewsRequestHandler.sendRequest(object : ResponseListener<PerformerReview>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<PerformerReview>) {
                Log.i("USPJEH", response.data.toString())
                _performerReviews.value = response.data.toMutableList()
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.i("NEUSPJEH", response.error_message)
                _errorMessage.value = response.message
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.i("NETWORK", "Network failure....")
                _errorMessage.value = t.message
            }
        })
    }
}