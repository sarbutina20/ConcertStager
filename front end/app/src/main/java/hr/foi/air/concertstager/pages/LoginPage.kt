package hr.foi.air.concertstager.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hr.foi.air.concertstager.R
import hr.foi.air.concertstager.core.login.LoginHandler
import hr.foi.air.concertstager.manual_login.ManualLoginHandler
import hr.foi.air.concertstager.manual_login.ManualLoginToken
import hr.foi.air.concertstager.ui.components.BigButton
import hr.foi.air.concertstager.ui.components.ButtonWithIcon
import hr.foi.air.concertstager.ui.components.LineDivider
import hr.foi.air.concertstager.ui.components.PasswordTextField
import hr.foi.air.concertstager.ui.components.RoundedBackgroundBox
import hr.foi.air.concertstager.ui.components.StyledOneLineTextField
import hr.foi.air.concertstager.ui.theme.ConcertStagerTheme
import hr.foi.air.concertstager.viewmodels.Auth.GoogleLoginViewModel
import hr.foi.air.concertstager.viewmodels.Auth.ManualLoginViewModel

@Composable
fun LoginPage(
    onSuccessfulLogin: () -> Unit,
    loginHandlers: List<LoginHandler>,
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit,
    manualLoginViewModel: ManualLoginViewModel = viewModel(),
    googleLoginViewModel: GoogleLoginViewModel = viewModel()
) {
    val manualLoginHandler : ManualLoginHandler = loginHandlers.first { it is ManualLoginHandler } as ManualLoginHandler

    val email = manualLoginViewModel.email.observeAsState().value ?: ""
    val password = manualLoginViewModel.password.observeAsState().value ?: ""

    var awaitingResponse by remember { mutableStateOf(false) }
    val errorMessage = manualLoginViewModel.errorMessage.observeAsState().value ?: ""

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        RoundedBackgroundBox(
            fontSize = 55,
            contentText = "CONCERT STAGER")
        Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = errorMessage,
                fontStyle = FontStyle.Normal,
                color = Color.Red,
                modifier = modifier
                    .align(Alignment.Start)
                    .padding(start = 40.dp)
            )

        StyledOneLineTextField(
            label = "Email",
            value = email,
            onValueChange = {
                manualLoginViewModel.email.value = it
            }
        )
        PasswordTextField(
            value = password,
            onValueChange ={
                manualLoginViewModel.password.value = it
            } )
        //Spacer(modifier = Modifier.height(0.dp))
        BigButton(
            label = "Log in",
            enabled = !awaitingResponse,
            onClick = {
                    val manualLoginToken = ManualLoginToken(email, password)
                    awaitingResponse = true
                    manualLoginViewModel.login(
                        manualLoginHandler,
                        manualLoginToken,
                        onSuccessfulLogin = {
                            awaitingResponse = false
                            onSuccessfulLogin()
                        },
                        onFailedLogin = {
                            Log.e("MANUAL LOGIN FAILED","Login failed on login page!")
                            awaitingResponse = false
                        }
                    )
                awaitingResponse = false
            })
        LineDivider(text = "OR")
        ButtonWithIcon(
            onClick = {
                googleLoginViewModel.login(
                    loginHandler = loginHandlers[0],
                    onSuccessfulLogin = {
                        onSuccessfulLogin()
                    },
                    onFailedLogin = {
                        Log.e("LOGIN FAILED","Login failed on login page!")
                    }
                )
            }
            ,
            imageResource = R.drawable.google_icon
        )

        Text(
            text = "Don't have an account?",
            fontStyle = FontStyle.Italic,
            modifier = modifier
                .align(Alignment.Start)
                .padding(start = 40.dp)
        )
        BigButton(
            label = "Sign up",
            onClick = {
                onSignUpClick()
            })
    }

}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    ConcertStagerTheme {
        LoginPage(
            onSuccessfulLogin = {},
            loginHandlers = listOf(),
            onSignUpClick = {}
        )
    }
}