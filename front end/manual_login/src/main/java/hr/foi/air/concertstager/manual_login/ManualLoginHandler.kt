package hr.foi.air.concertstager.manual_login

import hr.foi.air.concertstager.core.login.LoginHandler
import hr.foi.air.concertstager.core.login.LoginOutcomeListener
import hr.foi.air.concertstager.core.login.LoginToken
import hr.foi.air.concertstager.core.login.LoginUserData
import hr.foi.air.concertstager.core.login.network.ResponseListener
import hr.foi.air.concertstager.core.login.network.models.ErrorResponseBody
import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.ManualLoginBody
import hr.foi.air.concertstager.ws.models.RegisteredUser
import hr.foi.air.concertstager.ws.request_handlers.Login.ManualLoginRequestHandler

class ManualLoginHandler : LoginHandler  {
    override fun handleLogin(loginToken: LoginToken?, loginListener: LoginOutcomeListener) {
        if(loginToken !is ManualLoginToken) {
            throw IllegalArgumentException("Must receive UsernamePasswordLoginToken instance for 'loginToken'!")
        }

        val authorizers = loginToken.getAuthorizers()
        val email = authorizers["email"]!!
        val password = authorizers["password"]!!

        val loginRequestHandler = ManualLoginRequestHandler(ManualLoginBody(1, email, password))

        loginRequestHandler.sendRequest(object:ResponseListener<RegisteredUser> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<RegisteredUser>) {
                val loginUserData = response.data[0]
                loginListener.onSuccessfulLogin(LoginUserData(
                    userId = loginUserData.id,
                    user_name = loginUserData.name,
                    user_email = loginUserData.email,
                    user_roleId = loginUserData.roleId,
                    user_google_id = loginUserData.googleId,
                    user_contact_number = loginUserData.contactNumber,
                    user_genreId = loginUserData.genreId,
                    jwt = loginUserData.jwt
                ))
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                loginListener.onFailedLogin(response.error_message)
            }

            override fun onNetworkFailure(t: Throwable) {
                loginListener.onFailedLogin(t.message ?: "Could not connect to network.")
            }
        })
        }
    }