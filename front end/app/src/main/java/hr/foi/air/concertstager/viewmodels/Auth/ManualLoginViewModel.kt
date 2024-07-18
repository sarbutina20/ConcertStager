package hr.foi.air.concertstager.viewmodels.Auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.LoginHandler
import hr.foi.air.concertstager.core.login.LoginOutcomeListener
import hr.foi.air.concertstager.core.login.LoginToken
import hr.foi.air.concertstager.core.login.LoginUserData
import hr.foi.air.concertstager.core.login.Validator

class ManualLoginViewModel : ViewModel() {
    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")
    private val _errorMessage: MutableLiveData<String> = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    fun login(
        loginHandler: LoginHandler,
        loginToken: LoginToken,
        onSuccessfulLogin: () -> Unit,
        onFailedLogin: () -> Unit
    ) {
        if(validateInputs(email.value!!))
            loginHandler.handleLogin(
                loginToken,
                object : LoginOutcomeListener {
                    override fun onSuccessfulLogin(loginUserData: LoginUserData) {
                        UserLoginContext.loggedUser = loginUserData
                        onSuccessfulLogin()
                    }

                    override fun onFailedLogin(reason: String) {
                        _errorMessage.value = reason
                        onFailedLogin()
                    }
                }
            )
    }

    fun validateInputs(email:String):Boolean {
        if(!Validator.validateEmail(email)){
            _errorMessage.value = "Wrong email format."
            return false
        }
        else {
            _errorMessage.value = ""
            return true
        }
    }
}