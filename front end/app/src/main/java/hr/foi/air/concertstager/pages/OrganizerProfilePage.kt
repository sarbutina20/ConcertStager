package hr.foi.air.concertstager.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.foi.air.concertstager.R
import hr.foi.air.concertstager.ui.components.OrganizerProfileColumn
import hr.foi.air.concertstager.ui.components.RoundedUserBox
import hr.foi.air.concertstager.ui.theme.ConcertStagerTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.viewmodels.Organizer.OrganizerProfileViewModel

@Composable
fun OrganizerProfilePage(
    id: Int,
    navController: NavController,
    viewModel : OrganizerProfileViewModel = viewModel(),
    ) {
    LaunchedEffect(id){
        viewModel.getOrganizer(id)
    }
    ConcertStagerTheme {
        // Koristite novu varijablu koja će zadržati trenutne vrijednosti
        val name = viewModel.name.observeAsState().value?:""
        val email = viewModel.email.observeAsState().value?:""
        val contactNumber = viewModel.contactNumber.observeAsState().value?:""

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RoundedUserBox(
                onBack = {navController.navigate("organizerEventsPage")},
                imageResource = R.drawable.default_organizer
            )
            if(UserLoginContext.loggedUser!!.userId!! == id) {
                OrganizerProfileColumn(
                    name = name,
                    email = email,
                    contactNumber = contactNumber,
                    onClickEdit = {navController.navigate("editProfilePage")}
                )
            }
            else {
                OrganizerProfileColumn(
                    name = name,
                    email = email,
                    contactNumber = contactNumber,
                    onClickEdit = {}
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowOrganizerProfilePage(
    modifier: Modifier = Modifier
){
    //OrganizerProfilePage()
}