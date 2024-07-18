package hr.foi.air.concertstager.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
fun OrganizerRegistrationPage(
    modifier: Modifier = Modifier,
    returnToPreviousPage: () -> Unit,
    viewModel: RegistrationViewModel = viewModel(),
    continueToNextPage: () -> Unit,
){
    var contactNumber by remember { mutableStateOf("") }
    val errorMessage = viewModel.errorMessage.observeAsState().value?:""

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        RoundedBackgroundBox(fontSize = 48, contentText = "Welcome to Concert Stager!")

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
            label = "Contact number: ",
            value = contactNumber,
            onValueChange ={
                contactNumber=it

            } )

        Row(
            modifier = modifier
                .padding(start = 40.dp, end = 40.dp)
                .fillMaxWidth()
                .align(Alignment.Start),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PreviousPageButton(
                onClick = {
                    returnToPreviousPage()
                          },
            )

            Spacer(modifier = Modifier.weight(1f))

            NextPageButton(
                onClick =
                {
                    UserLoginContext.loggedUser?.user_contact_number = contactNumber
                    continueToNextPage()
                },
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
fun ShowOrganizerRegistrationPage(
    modifier: Modifier = Modifier
){
    OrganizerRegistrationPage(
        returnToPreviousPage = {},
        continueToNextPage = {}
    )
}