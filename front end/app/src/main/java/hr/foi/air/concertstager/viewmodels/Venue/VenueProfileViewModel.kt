package hr.foi.air.concertstager.viewmodels.Venue

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.network.ResponseListener
import hr.foi.air.concertstager.core.login.network.models.ErrorResponseBody
import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.response.Concert
import hr.foi.air.concertstager.ws.models.response.User
import hr.foi.air.concertstager.ws.models.response.Venue
import hr.foi.air.concertstager.ws.models.response.VenueReview
import hr.foi.air.concertstager.ws.request_handlers.Concert.GetVenueConcertsRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.Review.GetVenueReviewsRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.User.GetAllUsersRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.Venue.GetVenueRequestHandler

class VenueProfileViewModel: ViewModel() {
    var originalName: MutableLiveData<String> = MutableLiveData("")
    var originalDescription: MutableLiveData<String> = MutableLiveData("")
    var originalCity: MutableLiveData<String> = MutableLiveData("")
    var originalCapacity: MutableLiveData<Int> = MutableLiveData()
    var originalAddress: MutableLiveData<String> = MutableLiveData("")

    var name: MutableLiveData<String> = MutableLiveData("")
    var description: MutableLiveData<String> = MutableLiveData("")
    var city: MutableLiveData<String> = MutableLiveData("")
    var address: MutableLiveData<String> = MutableLiveData("")
    var capacity: MutableLiveData<Int> = MutableLiveData()
    var userId: MutableLiveData<Int> = MutableLiveData()

    private val _users : MutableLiveData<List<User>> = MutableLiveData(mutableListOf())
    val users : MutableLiveData<List<User>> = _users

    private val _venueConcerts : MutableLiveData<List<Concert>> = MutableLiveData(mutableListOf())
    val venueConcerts : MutableLiveData<List<Concert>> = _venueConcerts

    private val _venueReviews: MutableLiveData<List<VenueReview>> = MutableLiveData(mutableListOf())
    val venueReviews : MutableLiveData<List<VenueReview>> = _venueReviews

    private val _errorMessage: MutableLiveData<String> = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    fun getVenue(id: Int){
        val getVenueRequestHandler = GetVenueRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", id)
        getVenueRequestHandler.sendRequest(object : ResponseListener<Venue>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Venue>) {
                Log.i("USPJEH", response.data.toString())
                name.value = response.data[0].name
                description.value = response.data[0].description
                city.value = response.data[0].city
                address.value = response.data[0].address
                capacity.value = response.data[0].capacity
                userId.value = response.data[0].userId
                originalName.value = name.value
                originalDescription.value = description.value
                originalCity.value = city.value
                originalCapacity.value = capacity.value
                originalAddress.value = address.value
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.i("NEUSPJEH", response.message)
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.i("NETWORK", "Network failed....")
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

    fun getVenueConcerts(id: Int){
        val getVenueConcertsRequestHandler = GetVenueConcertsRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", id)
        getVenueConcertsRequestHandler.sendRequest(object: ResponseListener<Concert>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Concert>) {
                Log.i("USPJEH", response.data.toString())
                _venueConcerts.value = response.data.toMutableList()
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.i("NEUSPJEH", response.message)
                _errorMessage.value = response.message
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.i("NETWORK", "Network failed....")
            }
        })
    }

    fun getVenueReviews(id: Int){
        val getVenueReviewsRequestHandler = GetVenueReviewsRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", id)
        getVenueReviewsRequestHandler.sendRequest(object: ResponseListener<VenueReview>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<VenueReview>) {
                Log.i("USPJEH", response.data.toString())
                _venueReviews.value = response.data.toMutableList()
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.i("NEUSPJEH", response.message)
                _errorMessage.value = response.message
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.i("NETWORK", "Network failure.....")
            }
        })
    }
}