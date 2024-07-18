package hr.foi.air.concertstager.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hr.foi.air.concertstager.R
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.Roles.UserRole
import hr.foi.air.concertstager.ui.components.EditableOrganizerProfileColumn
import hr.foi.air.concertstager.ui.components.EditablePerformerProfileColumn
import hr.foi.air.concertstager.ui.components.EditableVisitorProfileColumn
import hr.foi.air.concertstager.ui.components.ProfilePicture
import hr.foi.air.concertstager.ui.theme.ConcertStagerTheme
import hr.foi.air.concertstager.viewmodels.Organizer.EditOrganizerProfileViewModel
import hr.foi.air.concertstager.viewmodels.Performer.EditPerformerProfileViewModel
import hr.foi.air.concertstager.viewmodels.Visitor.EditVisitorProfileViewModel

@Composable
fun EditProfilePage(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    ConcertStagerTheme {
        val roleId: Int = UserLoginContext.loggedUser!!.user_roleId!!
        val editVisitorProfileViewModel: EditVisitorProfileViewModel = viewModel()
        val editOrganizerProfileViewModel : EditOrganizerProfileViewModel = viewModel()
        val editPerformerProfileViewModel : EditPerformerProfileViewModel = viewModel()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ){
                ProfilePicture(
                    imageResource = when (roleId) {
                        UserRole.Visitor.value -> R.drawable.default_visitor
                        UserRole.Performer.value -> R.drawable.default_concert_performer
                        else -> R.drawable.default_organizer
                    }
                )
            }
            when(roleId){
                UserRole.Visitor.value->{
                    val errorMessage = editVisitorProfileViewModel.errorMessage.observeAsState().value?:""
                    if(errorMessage.isNotEmpty()){
                        Row(
                            modifier = modifier
                                .padding(20.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = errorMessage, color = Color.Red)
                        }
                    }
                    val name = editVisitorProfileViewModel.name.observeAsState().value?:""
                    val email = editVisitorProfileViewModel.email.observeAsState().value?:""
                    EditableVisitorProfileColumn(
                        name = name,
                        email = email,
                        onNameChange = {editVisitorProfileViewModel.name.value = it},
                        onEmailChange = {editVisitorProfileViewModel.email.value = it},
                        onSaveClick = { editVisitorProfileViewModel.updateVisitor { navController.navigate("visitorProfile/${UserLoginContext.loggedUser!!.userId}") } },
                        onCancelClick = { editVisitorProfileViewModel.declineUpdate { navController.navigate("visitorProfile/${UserLoginContext.loggedUser!!.userId}") } })
                }
                UserRole.Performer.value->{
                    val errorMessage = editPerformerProfileViewModel.errorMessage.observeAsState().value?:""
                    if(errorMessage.isNotEmpty()){
                        Row(
                            modifier = modifier
                                .padding(20.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = errorMessage, color = Color.Red)
                        }
                    }
                    val name = editPerformerProfileViewModel.name.observeAsState().value?:""
                    val email = editPerformerProfileViewModel.email.observeAsState().value?:""
                    val genreNames by editPerformerProfileViewModel.genreNames.observeAsState()
                    editPerformerProfileViewModel.genreId.value = UserLoginContext.loggedUser!!.user_genreId!!
                    editPerformerProfileViewModel.getGenre()
                    EditablePerformerProfileColumn(
                        name = name,
                        email = email,
                        genre = genreNames!!,
                        onNameChange = {editPerformerProfileViewModel.name.value = it},
                        onEmailChange = {editPerformerProfileViewModel.email.value = it},
                        onSaveClick = { editPerformerProfileViewModel.updatePerformer { navController.navigate("performerProfile/${UserLoginContext.loggedUser!!.userId!!}") } },
                        onCancelClick = { editPerformerProfileViewModel.declineUpdate { navController.navigate("performerProfile/${UserLoginContext.loggedUser!!.userId!!}") } })
                }
                UserRole.Organizer.value->{
                    val errorMessage = editOrganizerProfileViewModel.errorMessage.observeAsState().value?:""
                    if(errorMessage.isNotEmpty()){
                        Row(
                            modifier = modifier
                                .padding(20.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = errorMessage, color = Color.Red)
                        }
                    }
                    val name = editOrganizerProfileViewModel.name.observeAsState().value?:""
                    val email = editOrganizerProfileViewModel.email.observeAsState().value?:""
                    val contactNumber = editOrganizerProfileViewModel.contactNumber.observeAsState().value?:""
                    EditableOrganizerProfileColumn(
                        name = name,
                        email = email,
                        contactNumber = contactNumber,
                        onNameChange = {editOrganizerProfileViewModel.name.value = it},
                        onEmailChange = {editOrganizerProfileViewModel.email.value = it},
                        onContactNumberChange = {editOrganizerProfileViewModel.contactNumber.value = it},
                        onSaveClick = { editOrganizerProfileViewModel.updateOrganizer { navController.navigate("organizerProfile/${UserLoginContext.loggedUser!!.userId}") } },
                        onCancelClick = { editOrganizerProfileViewModel.declineUpdate { navController.navigate("organizerProfile/${UserLoginContext.loggedUser!!.userId}") } }
                    )
                }
            }

        }

    }
}