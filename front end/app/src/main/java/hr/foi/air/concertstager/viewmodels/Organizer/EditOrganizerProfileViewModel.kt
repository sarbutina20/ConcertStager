package hr.foi.air.concertstager.viewmodels.Organizer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.Validator
import hr.foi.air.concertstager.core.login.network.ResponseListener
import hr.foi.air.concertstager.core.login.network.models.ErrorResponseBody
import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.OrganizerUpdateBody
import hr.foi.air.concertstager.ws.models.response.Organizer
import hr.foi.air.concertstager.ws.request_handlers.Organizer.UpdateOrganizerRequestHandler

class EditOrganizerProfileViewModel: ViewModel() {
    var originalName: MutableLiveData<String> = MutableLiveData(UserLoginContext.loggedUser!!.user_name)
    var originalEmail: MutableLiveData<String> = MutableLiveData(UserLoginContext.loggedUser!!.user_email)
    var originalContactNumber: MutableLiveData<String> = MutableLiveData(UserLoginContext.loggedUser!!.user_contact_number)

    var name: MutableLiveData<String> = MutableLiveData(UserLoginContext.loggedUser!!.user_name)
    var email: MutableLiveData<String> = MutableLiveData(UserLoginContext.loggedUser!!.user_email)
    var contactNumber: MutableLiveData<String> = MutableLiveData(UserLoginContext.loggedUser!!.user_contact_number)

    private val _errorMessage: MutableLiveData<String> = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    fun updateOrganizer(onSuccess: () -> Unit){
        val organizerUpdateBody = OrganizerUpdateBody(
            name = name.value,
            email = email.value,
            contactNumber = contactNumber.value
        )

        validateInputs(organizerUpdateBody)
        if(_errorMessage.value!!.isEmpty()){
            val requestHandler = UpdateOrganizerRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", UserLoginContext.loggedUser!!.userId!!, organizerUpdateBody)
            requestHandler.sendRequest(
                object : ResponseListener<Organizer> {
                    override fun onSuccessfulResponse(response: SuccessfulResponseBody<Organizer>) {
                        Log.i("USPJEH", response.data[0].toString())
                        originalEmail.value = organizerUpdateBody.email
                        originalName.value = organizerUpdateBody.name
                        originalContactNumber.value = organizerUpdateBody.contactNumber
                        UserLoginContext.loggedUser!!.user_email = originalEmail.value
                        UserLoginContext.loggedUser!!.user_name = originalName.value
                        UserLoginContext.loggedUser!!.user_contact_number = originalContactNumber.value
                        onSuccess()
                    }

                    override fun onErrorResponse(response: ErrorResponseBody) {
                        _errorMessage.value = response.message + " " + response.error_code
                        Log.i("NEUSPJEH", "Neuspjesno azuriranje organizera.")
                    }

                    override fun onNetworkFailure(t: Throwable) {
                        Log.i("INTERNET", "Neuspjesno. Greska u internetu")
                    }

                }
            )
        }
    }

    fun declineUpdate(onFail: () -> Unit){
        name.value = originalName.value
        email.value = originalEmail.value
        contactNumber.value = originalContactNumber.value
        _errorMessage.value = ""
        onFail()
    }

    fun validateInputs(organizerUpdateBody: OrganizerUpdateBody) {
        var allValidFormat = true
        if(!Validator.validateName(organizerUpdateBody.name!!)){
            _errorMessage.value = "Invalid name format"
            allValidFormat = false
        }
        if(!Validator.validateEmail(organizerUpdateBody.email!!)){
            _errorMessage.value = "Invalid email format"
            allValidFormat = false
        }

        if(!Validator.validateContactNumber(organizerUpdateBody.contactNumber!!)){
            _errorMessage.value = "Invalid contact number format"
            allValidFormat = false
        }

        if(allValidFormat){
            _errorMessage.value = ""
        }
    }
}