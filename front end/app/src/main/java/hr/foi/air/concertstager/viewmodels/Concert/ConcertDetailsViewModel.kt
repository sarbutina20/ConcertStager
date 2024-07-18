package hr.foi.air.concertstager.viewmodels.Concert


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.network.ResponseListener
import hr.foi.air.concertstager.core.login.network.models.ErrorResponseBody
import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.CreateConcertEntryBody
import hr.foi.air.concertstager.ws.models.response.Concert
import hr.foi.air.concertstager.ws.models.response.ConcertEntry
import hr.foi.air.concertstager.ws.models.response.Organizer
import hr.foi.air.concertstager.ws.models.response.Performer
import hr.foi.air.concertstager.ws.models.response.Review
import hr.foi.air.concertstager.ws.models.response.Venue
import hr.foi.air.concertstager.ws.request_handlers.Concert.GetConcertRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.Concert.GetPerformersForConcertRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.ConcertEntry.CreateConcertEntryRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.ConcertEntry.GetConcertEntriesRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.Organizer.GetOrganizerRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.Review.GetAllReviewsOfUserRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.Venue.GetVenueRequestHandler

class ConcertDetailsViewModel: ViewModel() {

    private val _concert : MutableLiveData<Concert> = MutableLiveData()
    val concert : MutableLiveData<Concert> = _concert

    private val _performersForConcert : MutableLiveData<List<Performer>> = MutableLiveData(mutableListOf())
    val performersForConcert : MutableLiveData<List<Performer>> = _performersForConcert
    private val _performersNames : MutableLiveData<List<String>> = MutableLiveData(mutableListOf())

    private val _venue : MutableLiveData<Venue> = MutableLiveData()
    val venue : MutableLiveData<Venue> = _venue

    private val _organizer : MutableLiveData<Organizer> = MutableLiveData()
    val organizer : MutableLiveData<Organizer> = _organizer

    private val _reviews : MutableLiveData<List<Review>> = MutableLiveData(mutableListOf())
    val reviews :  MutableLiveData<List<Review>> = _reviews

    private val _entries : MutableLiveData<List<ConcertEntry>> = MutableLiveData(mutableListOf())
    val entries : MutableLiveData<List<ConcertEntry>> = _entries

    private val _errorMessage : MutableLiveData<String> = MutableLiveData("")
    val errorMessage : MutableLiveData<String> = _errorMessage

    fun getConcert(id: Int){
        val getConcertRequestHandler = GetConcertRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", id)
        getConcertRequestHandler.sendRequest(object: ResponseListener<Concert>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Concert>) {
                Log.i("USPJEH", response.data[0].toString())
                _concert.value = response.data[0]
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.i("NEUSPJEH", response.message)
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.i("NETWORK", "Network failure....")
            }
        })
    }

    fun getOrganizer(id: Int){
        val getOrganizerRequestHandler = GetOrganizerRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", id)
        getOrganizerRequestHandler.sendRequest(object: ResponseListener<Organizer>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Organizer>) {
                Log.i("USPJEH", response.data[0].toString())
                _organizer.value = response.data[0]
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.i("NEUSPJEH", response.message)
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.i("NETWORK", "Network failure....")
            }
        })
    }

    fun getVenue(id: Int){
        val getVenueRequestHandler = GetVenueRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", id)
        getVenueRequestHandler.sendRequest(object: ResponseListener<Venue>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Venue>) {
                Log.i("USPJEH", response.data[0].toString())
                _venue.value = response.data[0]
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.i("NEUSPJEH", response.message)
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.i("NETWORK", "Network failure....")
            }
        })
    }

    fun getAllReviewsOfUser(){
        val getAllReviewsOfUserRequestHandler = GetAllReviewsOfUserRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", UserLoginContext.loggedUser!!.userId!!)
        getAllReviewsOfUserRequestHandler.sendRequest(object: ResponseListener<Review>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Review>) {
                Log.i("HAMPAR", response.data.toString())
                _reviews.value = response.data.toMutableList()
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.i("NEUSPJEH", response.error_message)
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.i("NETWORK", "Network failure......")
            }
        })
    }

    fun getPerformersForConcert(id: Int){
        val getPerformersForConcertRequestHandler = GetPerformersForConcertRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", id)
        getPerformersForConcertRequestHandler.sendRequest(object: ResponseListener<Performer>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Performer>) {
                Log.i("USPJEH", response.data.toString())
                _performersForConcert.value = response.data.toMutableList()
                _performersNames.value = _performersForConcert.value?.map { it.name!! } ?: emptyList()
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.i("NEUSPJEH", response.message)
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.i("NETWORK", "Network failure....")
            }
        })
    }

    fun createConcertEntry(onCreatedConcertEntry: () -> Unit){
        val createConcertEntryBody = CreateConcertEntryBody(
            UserLoginContext.loggedUser!!.userId,
            _concert.value!!.id
        )

        val createConcertEntryRequestHandler = CreateConcertEntryRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", createConcertEntryBody)
        createConcertEntryRequestHandler.sendRequest(object : ResponseListener<ConcertEntry>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<ConcertEntry>) {
                Log.i("USPJEH", response.data.toString())
                _errorMessage.value = ""
                onCreatedConcertEntry()
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

    fun getConcertEntries(id: Int){
        val getConcertEntriesRequestHandler = GetConcertEntriesRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", id)
        getConcertEntriesRequestHandler.sendRequest(object: ResponseListener<ConcertEntry>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<ConcertEntry>) {
                Log.i("USPJEH", response.data.toString())
                _entries.value = response.data.toMutableList()
                _errorMessage.value = ""
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.i("NEUSPJEH", response.error_message)
                _errorMessage.value = response.error_message
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.i("NETWORK", "Network failure....")
                _errorMessage.value = t.message
            }
        })
    }
}