package hr.foi.air.concertstager.viewmodels.Performer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.Validator
import hr.foi.air.concertstager.core.login.network.ResponseListener
import hr.foi.air.concertstager.core.login.network.models.ErrorResponseBody
import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.VenueReviewCreateBody
import hr.foi.air.concertstager.ws.models.response.VenueReview
import hr.foi.air.concertstager.ws.request_handlers.Review.CreateReviewVenueRequestHandler

class PerformerReviewPopUpViewModel: ViewModel() {
    var comment: MutableLiveData<String> = MutableLiveData("")
    var popup: MutableLiveData<Boolean> = MutableLiveData(true)

    private val _errorMessage : MutableLiveData<String> = MutableLiveData("")
    val errorMessage : MutableLiveData<String> = _errorMessage

    fun reviewVenue(venueReviewCreateBody: VenueReviewCreateBody){
        if(_errorMessage.value!!.isEmpty()){
            val createReviewVenueVenueRequestHandler = CreateReviewVenueRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", venueReviewCreateBody)
            createReviewVenueVenueRequestHandler.sendRequest(object: ResponseListener<VenueReview>{
                override fun onSuccessfulResponse(response: SuccessfulResponseBody<VenueReview>) {
                    Log.i("USPJEH", response.data.toString())
                    _errorMessage.value = ""
                    popup.value = false
                }

                override fun onErrorResponse(response: ErrorResponseBody) {
                    Log.i("NEUSPJEH", response.message)
                    _errorMessage.value = response.message
                }

                override fun onNetworkFailure(t: Throwable) {
                    Log.i("NETWORK", "NETWORK FAILURE....")
                }
            })
        }
    }
}