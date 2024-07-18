package hr.foi.air.concertstager.login_google_login

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import hr.foi.air.concertstager.core.login.LoginHandler
import hr.foi.air.concertstager.core.login.LoginOutcomeListener
import hr.foi.air.concertstager.core.login.LoginToken
import hr.foi.air.concertstager.core.login.LoginUserData
import hr.foi.air.concertstager.core.login.network.callers.userapi.UserApiCaller
import hr.foi.air.concertstager.core.login.network.callers.userapi.callbacks.ILoginUserDataCallback

class GoogleLogin(
    val context: Context,
    private val activityResultLauncher: ActivityResultLauncher<Intent>
) : LoginHandler {

    private var idGoogleToken: String? = null
    private var idTokenValidator : IdTokenValidator = IdTokenValidator()
    private var outcomeListener: LoginOutcomeListener? = null
    private val userApiCaller : UserApiCaller = UserApiCaller()
    private var account : GoogleSignInAccount? = null
    override fun handleLogin(loginToken: LoginToken?, outcomeListener: LoginOutcomeListener) {

        if(loginToken != null)  throw IllegalArgumentException("Must receive no token for Google Login")


        this.outcomeListener = outcomeListener
        //val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(context)
        if (account != null) {
            this.outcomeListener.let {
                loginuser(account!!, outcomeListener)
            }
        }
        else {
           startGoogleSignIn()
        }
    }

    private fun startGoogleSignIn(){
        // serverClientId for requestIdToken web client = "759654001215-35engom30hdklp8vd9apfua4pndtquhg.apps.googleusercontent.com"
        // android client = "759654001215-1g3qbhh186ovihg6idcoqtgttov5li5v.apps.googleusercontent.com"
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("759654001215-35engom30hdklp8vd9apfua4pndtquhg.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(context, gso)
        val signInIntent: Intent = mGoogleSignInClient.signInIntent

        Log.i("BeforeContext", "Before starting activity")
        activityResultLauncher.launch(signInIntent)
        Log.i("AfterContext", "After starting activity")
    }

    private fun loginuser(account : GoogleSignInAccount, outcomeListener: LoginOutcomeListener){
        idTokenValidator.getTokenValidity(account.idToken) {result ->
            if(result){
                Log.i("TOKEN VALID", "Google token ID is VALID.")
                userApiCaller.getUserFromDatabase(account.id, object : ILoginUserDataCallback {
                    override fun onUserReceived(userFromDatabase: LoginUserData?) {
                        if (userFromDatabase != null) {
                            Log.i("UserFromDatabase","${userFromDatabase.user_name}")
                            Log.i("Account picture","${account.photoUrl}")
                            outcomeListener.onSuccessfulLogin(
                                LoginUserData(userFromDatabase.userId,userFromDatabase.user_google_id, userFromDatabase.user_name ,userFromDatabase.user_email,userFromDatabase.user_roleId!!,userFromDatabase.user_genreId,userFromDatabase.user_contact_number, userFromDatabase.jwt!!)
                            )
                            Log.i("LOGIN SUCCESS", "${userFromDatabase.user_name} logged in")
                        }
                        else {
                            outcomeListener.onSuccessfulLogin(
                                LoginUserData(
                                    null,
                                    account.id,
                                    account.givenName + " " + account.familyName,
                                    account.email,
                                    null,
                                    null,
                                    null,
                                    null,
                                )
                            )
                        }
                    }
                })
            }
            else {
                Log.i("TOKEN INVALID", "Google token ID is NOT VALID.")
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "Invalid token.", Toast.LENGTH_LONG).show()
                    outcomeListener.onFailedLogin("Invalid token.")
                }
            }
        }
    }

    fun handleSignInResult(data: Intent?) {
        if (data != null) {
            try {
                this.account = GoogleSignIn.getSignedInAccountFromIntent(data)
                    .getResult(ApiException::class.java)
                if (account != null) {
                    // Handle the signed-in account
                    idGoogleToken = account!!.idToken
                    Log.i("GOOGLE ID TOKEN", idGoogleToken.toString())
                    outcomeListener?.let {
                        loginuser(account!!, it)
                    }
                } else {
                    Log.e("GoogleLogin", "Google Sign-In account is null")
                }
            } catch (e: ApiException) {
                when (e.statusCode) {
                    GoogleSignInStatusCodes.SIGN_IN_REQUIRED -> {
                            // can prompt the user
                    }
                    else -> {
                        Log.e(
                            "GoogleLogin",
                            "Google Sign-In failed with error code: ${e.statusCode}"
                        )
                    }
                }
            }
        }
    }
}

