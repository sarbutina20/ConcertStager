package hr.foi.air.concertstager.viewmodels.Visitor

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
import hr.foi.air.concertstager.ws.request_handlers.Concert.GetFinishedConcertsRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.Concert.GetVisitorUpcomingConcertsRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.Venue.GetVenuesRequestHandler

class VisitorHomepageViewModel : ViewModel() {
    var title : MutableLiveData<String> = MutableLiveData("Events")

    private val _upcomingConcerts : MutableLiveData<List<Concert>> = MutableLiveData(mutableListOf())
    val upcomingConcerts : LiveData<List<Concert>> = _upcomingConcerts

    private val _finishedConcerts : MutableLiveData<List<Concert>> = MutableLiveData(mutableListOf())
    val finishedConcerts: MutableLiveData<List<Concert>> = _finishedConcerts

    private val _venues : MutableLiveData<List<Venue>> = MutableLiveData(mutableListOf())
    val venues : MutableLiveData<List<Venue>> = _venues

    private val _searchResults: MutableLiveData<List<Concert>> = MutableLiveData(null)
    val searchResults: LiveData<List<Concert>> = _searchResults

    fun getUpcomingConcerts(){
        val getUpcomingConcertsRequestHandler = GetVisitorUpcomingConcertsRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}")
        getUpcomingConcertsRequestHandler.sendRequest(object : ResponseListener<Concert>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Concert>) {
                _upcomingConcerts.value = response.data.toMutableList()
                Log.i("USPJEH", _upcomingConcerts.value.toString())
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.e("NEUSPJEH", response.message + " " + response.error_code)
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.e("NETWORK", "Dogodila se greska s internetom")
            }
        })
    }

    fun searchUpcomingConcerts(query: String) {

        val filteredList = _upcomingConcerts.value?.filter { concert ->
            concert.name?.contains(query, ignoreCase = true) == true
        } ?: emptyList()

        _searchResults.value = filteredList
    }

    fun searchFinishedConcerts(query: String) {

        val filteredList = _finishedConcerts.value?.filter { concert ->
            concert.name?.contains(query, ignoreCase = true) == true
        } ?: emptyList()

        _searchResults.value = filteredList
    }

    fun resetSearch() {
        _searchResults.value = null
    }

    fun getFinishedConcerts(){
        val getFinishedConcertsRequestHandler = GetFinishedConcertsRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}")
        getFinishedConcertsRequestHandler.sendRequest(object : ResponseListener<Concert>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Concert>) {
                _finishedConcerts.value = response.data.toMutableList()
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.e("NEUSPJEH", response.message + " " + response.error_code)
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.e("NETWORK", "Dogodila se greska s internetom")
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