package hr.foi.air.concertstager.viewmodels.Visitor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.network.ResponseListener
import hr.foi.air.concertstager.core.login.network.models.ErrorResponseBody
import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.response.Visitor
import hr.foi.air.concertstager.ws.request_handlers.Visitor.GetVisitorRequestHandler

class VisitorProfileViewModel : ViewModel() {
    var originalName: MutableLiveData<String> = MutableLiveData("")
    var originalEmail: MutableLiveData<String> = MutableLiveData("")

    var name:MutableLiveData<String> = MutableLiveData("")
    var email:MutableLiveData<String> = MutableLiveData("")

    private val _errorMessage: MutableLiveData<String> = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    fun getVisitor(id: Int){
        val requestHandler = GetVisitorRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", id)
        requestHandler.sendRequest(object : ResponseListener<Visitor>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Visitor>) {
                val visitorDto = response.data[0]
                name.value = visitorDto.name
                email.value = visitorDto.email
                originalName.value = name.value
                originalEmail.value = email.value
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                _errorMessage.value = response.error_message
            }

            override fun onNetworkFailure(t: Throwable) {
                _errorMessage.value = "Network failure."
            }
        })
    }
}