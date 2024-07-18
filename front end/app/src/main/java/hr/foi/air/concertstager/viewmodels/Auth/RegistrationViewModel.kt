package hr.foi.air.concertstager.viewmodels.Auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.LoginUserData
import hr.foi.air.concertstager.core.login.Roles.UserRole
import hr.foi.air.concertstager.core.login.Validator
import hr.foi.air.concertstager.core.login.network.ResponseListener
import hr.foi.air.concertstager.core.login.network.models.ErrorResponseBody
import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.CreateEventBody
import hr.foi.air.concertstager.ws.models.RegisteredUser
import hr.foi.air.concertstager.ws.models.RegistrationBody
import hr.foi.air.concertstager.ws.request_handlers.Registration.RegistrationRequestHandler

class RegistrationViewModel : ViewModel() {
    val userName: MutableLiveData<String> = MutableLiveData("")
    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")
    val genreId: MutableLiveData<Int> = MutableLiveData(null)
    val userContactNumber: MutableLiveData<String> = MutableLiveData("")

    var validationCheck : Boolean = true

    private val _errorMessage: MutableLiveData<String> = MutableLiveData("")
    val errorMessage: MutableLiveData<String> = _errorMessage

    fun registerVisitor(onSuccess: () -> Unit, onFail: (errorMessage: String) -> Unit) {
        val requestBody = RegistrationBody(
            googleId = null,
            userName.value!!,
            email.value!!,
            password.value!!,
            roleId = 3,
            genreId = null,
            contactNumber = null
        )
        if(validateInputs(requestBody))
        {
            Log.d("RegistrationViewModel", "Visitor Request Body: $requestBody")
            registrationForAllUsers(requestBody, onSuccess, onFail)
        }
    }

    fun registerPerformer(onSuccess: () -> Unit, onFail: (errorMessage: String) -> Unit) {
        val requestBody = RegistrationBody(
            googleId = null,
            userName.value!!,
            email.value!!,
            password.value!!,
            roleId = 2,
            genreId.value!!,
            contactNumber = null
        )
        if(validateInputs(requestBody))
        {
            Log.d("RegistrationViewModel", "Visitor Request Body: $requestBody")
            registrationForAllUsers(requestBody, onSuccess, onFail)
        }
    }
    fun registerOrganizer(onSuccess: () -> Unit, onFail: (errorMessage: String) -> Unit) {
        val requestBody = RegistrationBody(
            googleId = null,
            userName.value!!,
            email.value!!,
            password.value!!,
            roleId = 1,
            genreId = null,
            userContactNumber.value!!
        )
        if(validateInputs(requestBody))
        {
            Log.d("RegistrationViewModel", "Visitor Request Body: $requestBody")
            registrationForAllUsers(requestBody, onSuccess, onFail)
        }
    }

    fun registerFromGoogle(loginUserData: LoginUserData,onSuccess: () -> Unit, onFail: (errorMessage: String) -> Unit) {
        val rb = loginUserData.user_roleId?.let {
            RegistrationBody(
                loginUserData.user_google_id,
                loginUserData.user_name,
                loginUserData.user_email,
                null,
                it,
                loginUserData.user_genreId,
                loginUserData.user_contact_number
            )
        }

        Log.d("RegistrationViewModel", "GoogleRegistration: $rb")
        if (rb != null) {
            registrationForAllUsers(rb, onSuccess, onFail)
        }
    }

    private fun registrationForAllUsers(requestBody: RegistrationBody, onSuccess: () -> Unit, onFail: (errorMessage: String) -> Unit) {
        val registrationRequestHandler = RegistrationRequestHandler(requestBody)

        registrationRequestHandler.sendRequest(object : ResponseListener<RegisteredUser> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<RegisteredUser>) {
                Log.d("RegistrationViewModel", "User registered!")
                val user = response.data[0]
                Log.i("KORISNIK", user.toString())
                fillInLoggedUserData(user)
                _errorMessage.value = ""
                onSuccess()
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                _errorMessage.value = response.message + " "
                onFail(_errorMessage.value!!)
            }

            override fun onNetworkFailure(t: Throwable) {
                _errorMessage.value = "Network error! Please try again later!"
                onFail(_errorMessage.value!!)
            }
        })
    }

    private fun fillInLoggedUserData(user: RegisteredUser) {
        UserLoginContext.loggedUser?.user_google_id = null;
        UserLoginContext.loggedUser?.userId = user.id
        UserLoginContext.loggedUser?.user_name = user.name
        UserLoginContext.loggedUser?.user_email = user.email
        //UserLoginContext.loggedUser?.user_password = user.password
        UserLoginContext.loggedUser?.user_roleId = user.roleId
        UserLoginContext.loggedUser?.user_genreId = user.genreId
        UserLoginContext.loggedUser?.user_contact_number = user.contactNumber;
        UserLoginContext.loggedUser?.jwt = user.jwt
    }

    private fun validateInputs(requestBody: RegistrationBody): Boolean {
        var allValid = true

        if(!Validator.validateName(requestBody.name!!)){
            _errorMessage.value = "Wrong name format."
            allValid = false
        }

        if(userName.value == ""){
            _errorMessage.value = "You need to fill all information."
            allValid = false
        }

        if(!Validator.validatePassword(requestBody.password!!)){
            _errorMessage.value = "Wrong password format."
            allValid = false
        }

        if(!Validator.validateEmail(requestBody.email!!)){
            _errorMessage.value = "Wrong email format."
            allValid = false
        }

        when(requestBody.roleId)
        {
            UserRole.Organizer.value -> {
                if(!Validator.validateContactNumber(requestBody.contactNumber!!)){
                    _errorMessage.value = "Wrong contact number format."
                    allValid = false
                }
            }
        }

        if(allValid){
            _errorMessage.value = ""
        }

        return allValid
    }
}