package hr.foi.air.concertstager.viewmodels.Organizer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.network.ResponseListener
import hr.foi.air.concertstager.core.login.network.models.ErrorResponseBody
import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.response.Organizer
import hr.foi.air.concertstager.ws.request_handlers.Organizer.GetOrganizerRequestHandler


class OrganizerProfileViewModel : ViewModel() {
    var originalName: MutableLiveData<String> = MutableLiveData("")
    var originalEmail: MutableLiveData<String> = MutableLiveData("")
    var originalContactNumber: MutableLiveData<String> = MutableLiveData("")

    var name:MutableLiveData<String> = MutableLiveData("")
    var email:MutableLiveData<String> = MutableLiveData("")
    var contactNumber: MutableLiveData<String> = MutableLiveData("")

    private val _errorMessage: MutableLiveData<String> = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    fun getOrganizer(id: Int){
        val requestHandler = GetOrganizerRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", id)
        requestHandler.sendRequest(object : ResponseListener<Organizer>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Organizer>) {
                val organizerDto = response.data[0]
                name.value = organizerDto.name
                email.value = organizerDto.email
                contactNumber.value = organizerDto.contactNumber
                originalName.value = name.value
                originalEmail.value = email.value
                originalContactNumber.value = contactNumber.value
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