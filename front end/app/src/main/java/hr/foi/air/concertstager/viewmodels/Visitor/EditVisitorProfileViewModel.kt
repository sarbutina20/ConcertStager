package hr.foi.air.concertstager.viewmodels.Visitor
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.Validator
import hr.foi.air.concertstager.core.login.network.ResponseListener
import hr.foi.air.concertstager.core.login.network.models.ErrorResponseBody
import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.VisitorUpdateBody
import hr.foi.air.concertstager.ws.models.response.Visitor
import hr.foi.air.concertstager.ws.request_handlers.Visitor.UpdateVisitorRequestHandler

class EditVisitorProfileViewModel: ViewModel() {
    var originalName: MutableLiveData<String> = MutableLiveData(UserLoginContext.loggedUser!!.user_name)
    var originalEmail: MutableLiveData<String> = MutableLiveData(UserLoginContext.loggedUser!!.user_email)

    var name: MutableLiveData<String> = MutableLiveData(UserLoginContext.loggedUser!!.user_name)
    var email: MutableLiveData<String> = MutableLiveData(UserLoginContext.loggedUser!!.user_email)

    private val _errorMessage: MutableLiveData<String> = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    fun updateVisitor(onSuccess: () -> Unit){
        val visitorUpdateBody = VisitorUpdateBody(
            name.value!!,
            email.value!!,
        )

        validateInputs(visitorUpdateBody)
        if(_errorMessage.value!!.isEmpty()){
            val requestHandler = UpdateVisitorRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", UserLoginContext.loggedUser!!.userId!!, visitorUpdateBody)
            requestHandler.sendRequest(
                object : ResponseListener<Visitor> {
                    override fun onSuccessfulResponse(response: SuccessfulResponseBody<Visitor>) {
                        originalName.value = visitorUpdateBody.name
                        originalEmail.value = visitorUpdateBody.email
                        UserLoginContext.loggedUser!!.user_name = originalName.value
                        UserLoginContext.loggedUser!!.user_email = originalEmail.value
                        Log.i("USPJEH", "USPJESNO AZURIRAN " + response.data[0].toString())
                        onSuccess()
                    }

                    override fun onErrorResponse(response: ErrorResponseBody) {
                        _errorMessage.value = response.error_message + " " + response.error_code
                        Log.i("NEUSPJEH", "Neuspjesno azuriran")
                    }

                    override fun onNetworkFailure(t: Throwable) {
                        _errorMessage.value = "Pogreska u internetu"
                        Log.i("INTERNET", "Greska u spajanju na internet")
                    }

                }
            )
        }
    }

    fun declineUpdate(onFail: () -> Unit){
        name.value = originalName.value
        email.value = originalEmail.value
        _errorMessage.value = ""
        onFail()
    }

    private fun validateInputs(visitorUpdateBody: VisitorUpdateBody){
        var allValidFormat = true
        if(!Validator.validateName(visitorUpdateBody.name!!)){
            _errorMessage.value = "Invalid name format"
            allValidFormat = false
        }
        if(!Validator.validateEmail(visitorUpdateBody.email!!)){
            _errorMessage.value = "Invalid email format"
            allValidFormat = false
        }

        if(allValidFormat){
            _errorMessage.value = ""
        }
    }
}