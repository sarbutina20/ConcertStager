package hr.foi.air.concertstager.core.login.network.callers.userapi.callbacks

import hr.foi.air.concertstager.core.login.LoginUserData

interface ILoginUserDataCallback {
    fun onUserReceived(user: LoginUserData?)
}