package hr.foi.air.concertstager.pages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.Roles.UserRole
import hr.foi.air.concertstager.core.login.network.callers.userapi.UserApiCaller
import hr.foi.air.concertstager.ui.components.DetailsButton
import hr.foi.air.concertstager.ui.components.PreviousPageButton
import hr.foi.air.concertstager.ui.components.RoundedBackgroundBox
import hr.foi.air.concertstager.viewmodels.Auth.RegistrationViewModel


val userApiCaller = UserApiCaller()

@Composable
fun SuccessfulRegistrationPage(
    modifier: Modifier = Modifier,
    name: String?,
    role: Int?,
    goToHomePageClick: () -> Unit,
    goBackToRegistration: (role: Int) -> Unit,
    registrationViewModel: RegistrationViewModel = viewModel()
){
    var isRegistrationSuccessful by remember { mutableStateOf(false) }
    if (isRegistrationSuccessful){
        goToHomePageClick()
    }

    fun getStringRole(role : Int) : String {
        var roleString : String = "Unknown"
        when (role) {
            1 -> roleString = "Organizer"
            2 -> roleString = "Performer"
            3 -> roleString = "Visitor"
        }
        return roleString
    }


    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundedBackgroundBox(
            fontSize = 38,
            contentText = "$name, you will be logged in as a ${role?.let { getStringRole(it) }}!",
            modifier = modifier
                .fillMaxWidth())
        DetailsButton(
            label = "Go to app!",
            onClick = {
                if(UserLoginContext.loggedUser?.user_google_id == null)
                {
                    goToHomePageClick()
                }
                else
                {

                    /*userApiCaller.registerUser(UserLoginContext.loggedUser!!) { result ->
                    if(result){
                        Log.i("REGISTERED USER", "${UserLoginContext.loggedUser!!.user_email}")
                        isRegistrationSuccessful = true
                    }
                    else {
                        Log.i("REGISTERED USER", "Failed to register the user")
                    }
                }*/

                    registrationViewModel.registerFromGoogle(UserLoginContext.loggedUser!!,
                        onSuccess = {
                            isRegistrationSuccessful = true
                                    },
                        onFail = { errorMessage ->
                            Log.i("GoogleRegistrationViewModel", errorMessage)}
                    )
                }
                },
            modifier = modifier
                .padding(top = 180.dp)
        )
        Row(
            modifier = modifier
                .padding(top = 180.dp, start = 40.dp)
                .align(Alignment.Start),
        ) {
            PreviousPageButton(
                onClick = {
                    when (role) {
                        2 -> goBackToRegistration(2)
                        3 -> goBackToRegistration(3)
                        1 -> goBackToRegistration(1)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun showSuccessfulRegistrationPage(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            //.fillMaxWidth()
    ) {
        SuccessfulRegistrationPage(
            name = "Ivan",
            role = 1,
            goToHomePageClick = {},
            goBackToRegistration = {})
    }
}