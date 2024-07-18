package hr.foi.air.concertstager.viewmodels.Concert

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.DateFormatter
import hr.foi.air.concertstager.core.login.Validator
import hr.foi.air.concertstager.core.login.network.ResponseListener
import hr.foi.air.concertstager.core.login.network.models.ErrorResponseBody
import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.CreateEventBody
import hr.foi.air.concertstager.ws.models.response.Concert
import hr.foi.air.concertstager.ws.models.response.Venue
import hr.foi.air.concertstager.ws.request_handlers.Concert.CreateConcertRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.Venue.GetVenuesRequestHandler
import java.util.Date

class CreateConcertViewModel: ViewModel() {
    var name : MutableLiveData<String> = MutableLiveData("")
    var startDate : MutableLiveData<String> = MutableLiveData(DateFormatter.getDate(Date().toString()))
    var endDate: MutableLiveData<String> = MutableLiveData(DateFormatter.getDate(Date().toString()))
    var description: MutableLiveData<String> = MutableLiveData("")
    var venueId: MutableLiveData<Int> = MutableLiveData()
    var selectedVenue: MutableLiveData<Venue> = MutableLiveData()

    private val _venues : MutableLiveData<List<Venue>> = MutableLiveData(mutableListOf())
    val venues: MutableLiveData<List<Venue>> = _venues

    private val _errorMessage: MutableLiveData<String> = MutableLiveData("")
    val errorMessage : MutableLiveData<String> = _errorMessage

    fun getVenues(){
        val getVenuesRequestHandler = GetVenuesRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}")
        getVenuesRequestHandler.sendRequest(object: ResponseListener<Venue>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Venue>) {
                _venues.value = response.data.toMutableList()
                selectedVenue.value = response.data[0]
                _errorMessage.value = ""
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                _errorMessage.value = response.message
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.e("NETWORK", "Network failed....")
            }
        })
    }

    fun createConcert(onSaveClicked: () -> Unit){
        val createConcertBody = CreateEventBody(
            venueId = selectedVenue.value!!.id,
            userId = UserLoginContext.loggedUser!!.userId,
            description = description.value,
            name = name.value,
            startDate = DateFormatter.saveDate(startDate.value.toString()),
            endDate = DateFormatter.saveDate(endDate.value.toString())
        )
        validateInputs(createConcertBody)
        if(_errorMessage.value!!.isEmpty()){
            val createEventRequestHandler = CreateConcertRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", createConcertBody)
            createEventRequestHandler.sendRequest(object: ResponseListener<Concert>{
                override fun onSuccessfulResponse(response: SuccessfulResponseBody<Concert>) {
                    _errorMessage.value = ""
                    onSaveClicked()
                }

                override fun onErrorResponse(response: ErrorResponseBody) {
                    Log.e("NEUSPJEH", response.toString())
                    //_errorMessage.value = response.message
                }

                override fun onNetworkFailure(t: Throwable) {
                    Log.i("NETWORK", "Network failure....")
                    _errorMessage.value = t.message
                }
            })
        }
    }

    private fun validateInputs(createConcertBody: CreateEventBody) {
        var allValid = true

        if(!Validator.validateName(createConcertBody.name!!)){
            _errorMessage.value = "Wrong name format."
            allValid = false
        }

        if(!Validator.validateComment(createConcertBody.description!!)){
            _errorMessage.value = "Wrong description format."
            allValid = false
        }

        if(startDate.value!! > endDate.value!!){
            _errorMessage.value = "Wrong dates"
            allValid = false
        }

        if(name.value == "" || description.value == ""){
            _errorMessage.value = "You need to fill all informations."
            allValid = false
        }

        if(allValid){
            _errorMessage.value = ""
        }
    }
}