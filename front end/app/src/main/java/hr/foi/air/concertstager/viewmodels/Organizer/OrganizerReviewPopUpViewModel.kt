package hr.foi.air.concertstager.viewmodels.Organizer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.Validator
import hr.foi.air.concertstager.core.login.network.ResponseListener
import hr.foi.air.concertstager.core.login.network.models.ErrorResponseBody
import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.PerformerReviewCreateBody
import hr.foi.air.concertstager.ws.models.response.PerformerReview
import hr.foi.air.concertstager.ws.request_handlers.Review.CreateReviewPerformerRequestHandler

class OrganizerReviewPopUpViewModel: ViewModel() {
    var comment: MutableLiveData<String> = MutableLiveData("")
    var popup: MutableLiveData<Boolean> = MutableLiveData(true)

    private val _errorMessage : MutableLiveData<String> = MutableLiveData("")
    val errorMessage : MutableLiveData<String> = _errorMessage

    fun reviewPerformer(performerReviewCreateBody: PerformerReviewCreateBody){
        if(_errorMessage.value!!.isEmpty()){
            val performerReviewsRequestHandler = CreateReviewPerformerRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", performerReviewCreateBody)
            performerReviewsRequestHandler.sendRequest(object : ResponseListener<PerformerReview> {
                override fun onSuccessfulResponse(response: SuccessfulResponseBody<PerformerReview>) {
                    _errorMessage.value = ""
                    popup.value = false
                }

                override fun onErrorResponse(response: ErrorResponseBody) {
                    _errorMessage.value = response.message
                }

                override fun onNetworkFailure(t: Throwable) {
                    _errorMessage.value = "Network failure...."
                }
            })
        }
    }
}