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
import hr.foi.air.concertstager.ws.models.response.Venue
import hr.foi.air.concertstager.ws.request_handlers.Concert.GetAcceptedConcertsForPerformerRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.Concert.GetPendingConcertsForPerformerRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.Concert.GetUpcomingConcertsRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.Venue.GetVenuesRequestHandler

class PerformerHomepageViewModel : ViewModel() {
    var selectedTitle : MutableLiveData<String> = MutableLiveData("Events")

    private val _upcomingConcerts : MutableLiveData<List<Concert>> = MutableLiveData(mutableListOf())
    val upcomingConcerts : LiveData<List<Concert>> = _upcomingConcerts

    private val _acceptedConcerts : MutableLiveData<List<Concert>> = MutableLiveData(mutableListOf())
    val acceptedConcerts: MutableLiveData<List<Concert>> = _acceptedConcerts

    private val _pendingConcerts : MutableLiveData<List<Concert>> = MutableLiveData(mutableListOf())
    val pendingConcerts: MutableLiveData<List<Concert>> = _pendingConcerts

    private val _venues : MutableLiveData<List<Venue>> = MutableLiveData(mutableListOf())
    val venues : MutableLiveData<List<Venue>> = _venues

    private val _searchResults: MutableLiveData<List<Concert>> = MutableLiveData()
    val searchResults: LiveData<List<Concert>> = _searchResults

    fun getUpcomingConcerts(){
        val getUpcomingConcertsRequestHandler = GetUpcomingConcertsRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}")
        getUpcomingConcertsRequestHandler.sendRequest(object : ResponseListener<Concert> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Concert>) {
                _upcomingConcerts.value = response.data.toMutableList()
                Log.i("USPJEH", _upcomingConcerts.value.toString())
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.i("NEUSPJEH", response.message + " " + response.error_code)
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.i("NETWORK", "Dogodila se greska s internetom")
            }
        })
    }

    fun performSearch(query: String) {

        val filteredList = _upcomingConcerts.value?.filter { concert ->
            concert.name?.contains(query, ignoreCase = true) == true
        } ?: emptyList()

        _searchResults.value = filteredList
        Log.e("performSearch","I am search for ${_searchResults.value}")
    }

    fun getAcceptedConcertsForPerformer(){
        val getAcceptedConcertsForPerformerRequestHandler = GetAcceptedConcertsForPerformerRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", UserLoginContext.loggedUser!!.userId!!)
        getAcceptedConcertsForPerformerRequestHandler.sendRequest(object : ResponseListener<Concert> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Concert>) {
                _acceptedConcerts.value = response.data.toMutableList()
                Log.i("USPJEH", _acceptedConcerts.value.toString())
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.i("NEUSPJEH", response.message + " " + response.error_code)
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.i("NETWORK", "Dogodila se greska s internetom")
            }
        })
    }

    fun getPendingConcertsForPerformer(){
        val getPendingConcertsForPerformerRequestHandler = GetPendingConcertsForPerformerRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", UserLoginContext.loggedUser!!.userId!!)
        getPendingConcertsForPerformerRequestHandler.sendRequest(object : ResponseListener<Concert> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Concert>) {
                _pendingConcerts.value = response.data.toMutableList()
                Log.i("USPJEH", _pendingConcerts.value.toString())
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.i("NEUSPJEH", response.message + " " + response.error_code)
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.i("NETWORK", "Dogodila se greska s internetom")
            }
        })
    }


    fun getVenues(){
        val getVenuesRequestHandler = GetVenuesRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}")
        getVenuesRequestHandler.sendRequest(object : ResponseListener<Venue>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Venue>) {
                Log.i("USPJEH", response.data.toString())
                _venues.value = response.data.toMutableList()
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.i("NEUSPJEH", response.message + " " + response.error_code)
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.i("NETWORK", "Failed to load resource...")
            }
        })
    }
}