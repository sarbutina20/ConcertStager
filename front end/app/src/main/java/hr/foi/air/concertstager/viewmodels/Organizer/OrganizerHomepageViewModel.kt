package hr.foi.air.concertstager.viewmodels.Organizer

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
import hr.foi.air.concertstager.ws.request_handlers.Concert.GetOrganizerConcertsRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.Venue.GetVenuesRequestHandler

class OrganizerHomepageViewModel: ViewModel() {

    private val _organizerConcerts : MutableLiveData<List<Concert>> = MutableLiveData(mutableListOf())
    val organizerConcerts : LiveData<List<Concert>> = _organizerConcerts

    private val _venues : MutableLiveData<List<Venue>> = MutableLiveData(mutableListOf())
    val venues : MutableLiveData<List<Venue>> = _venues

    private val _searchResults: MutableLiveData<List<Concert>> = MutableLiveData()
    //val searchResults: LiveData<List<Concert>> = _searchResults

    fun getOrganizerConcerts(){
        val getOrganizerConcerts = GetOrganizerConcertsRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", UserLoginContext.loggedUser!!.userId!!)
        getOrganizerConcerts.sendRequest(object : ResponseListener<Concert> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Concert>) {
                _organizerConcerts.value = response.data.toMutableList()
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.e("NEUSPJEH", response.message + " " + response.error_code)
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.e("NETWORK", "Dogodila se greska s internetom")
            }
        })
    }

    /*fun performSearch(query: String) {
        val filteredList = _organizerConcerts.value?.filter { concert ->
            concert.name?.contains(query, ignoreCase = true) == true
        } ?: emptyList()

        _searchResults.value = filteredList
    }*/

    fun getVenues(){
        val getVenuesRequestHandler = GetVenuesRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}")
        getVenuesRequestHandler.sendRequest(object : ResponseListener<Venue>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Venue>) {
                _venues.value = response.data.toMutableList()
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.e("NEUSPJEH", response.message + " " + response.error_code)
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.e("NETWORK", "Failed to load resource...")
            }
        })
    }
}