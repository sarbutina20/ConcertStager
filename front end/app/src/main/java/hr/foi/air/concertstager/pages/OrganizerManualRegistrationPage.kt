package hr.foi.air.concertstager.pages

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.ui.components.NextPageButton
import hr.foi.air.concertstager.ui.components.PreviousPageButton
import hr.foi.air.concertstager.ui.components.RoundedBackgroundBox
import hr.foi.air.concertstager.ui.components.StyledOneLineTextField
import hr.foi.air.concertstager.viewmodels.Auth.RegistrationViewModel

@Composable
fun OrganizerManualRegistrationPage(
    modifier: Modifier = Modifier,
    returnToRoleChoosing: () -> Unit,
    viewModel: RegistrationViewModel = viewModel(),
    onSuccessfulRegistration: () -> Unit
){
    val name = viewModel.userName.observeAsState().value ?: ""
    val emailAddress = viewModel.email.observeAsState().value ?: ""
    val password = viewModel.password.observeAsState().value ?: ""
    val contactNumber = viewModel.userContactNumber.observeAsState().value ?: ""
    val errorMessage = viewModel.errorMessage.observeAsState().value?:""

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val context = LocalContext.current
        RoundedBackgroundBox(fontSize = 48, contentText = "Welcome to the registration page!")

        if(errorMessage.isNotEmpty()){
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = errorMessage, color = Color.Red)
            }
        }

        StyledOneLineTextField(
            label = "Name: ",
            value = name,
            onValueChange ={
                viewModel.userName.value = it
            } )
        StyledOneLineTextField(
            label = "Password: ",
            imageVector = Icons.Default.Lock,
            value = password,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange ={
                viewModel.password.value=it
            } )
        StyledOneLineTextField(
            label = "Email adress: ",
            imageVector = Icons.Default.Email,
            value = emailAddress,
            onValueChange ={
                viewModel.email.value=it
            } )

        StyledOneLineTextField(
            label = "Contact number: ",
            imageVector = Icons.Default.Phone,
            value = contactNumber,
            onValueChange ={
                viewModel.userContactNumber.value=it
            } )
        Row(
            modifier = modifier
                .padding(start = 40.dp, end = 40.dp)
                .fillMaxWidth()
                .align(Alignment.Start),
        ) {
            PreviousPageButton(
                onClick = { returnToRoleChoosing() }
            )

            Spacer(modifier = Modifier.weight(1f))

            NextPageButton(
                onClick = {
                        println("Logged User: ${UserLoginContext.loggedUser}")
                        viewModel.registerOrganizer(
                            onSuccess = {
                                onSuccessfulRegistration()
                            },
                            onFail = { errorMessage ->
                                Toast.makeText(context, "$errorMessage!", Toast.LENGTH_LONG).show()}
                        )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowOrganizerManualRegistrationPage(
    modifier: Modifier = Modifier
){
    OrganizerManualRegistrationPage(
        returnToRoleChoosing = {},
        onSuccessfulRegistration = {}
    )
}