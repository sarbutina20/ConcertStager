package hr.foi.air.concertstager.viewmodels.Auth

import android.util.Log
import androidx.lifecycle.ViewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.LoginHandler
import hr.foi.air.concertstager.core.login.LoginOutcomeListener
import hr.foi.air.concertstager.core.login.LoginUserData

class GoogleLoginViewModel : ViewModel() {
    fun login(
        loginHandler: LoginHandler,
        onSuccessfulLogin: () -> Unit,
        onFailedLogin: () -> Unit
    ) {
        loginHandler.handleLogin(null, object : LoginOutcomeListener {
            override fun onSuccessfulLogin(loginUserData: LoginUserData) {
                UserLoginContext.loggedUser = loginUserData
                onSuccessfulLogin()
            }

            override fun onFailedLogin(reason: String) {
                Log.e("onFailedLoginLoginViewModel", reason);
                onFailedLogin()
            }
        })
    }
}