package hr.foi.air.concertstager.pages

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
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.LoginUserData
import hr.foi.air.concertstager.core.login.Roles.UserRole
import hr.foi.air.concertstager.ui.components.CardWithIcons
import hr.foi.air.concertstager.ui.components.PreviousPageButton
import hr.foi.air.concertstager.ui.components.RoundedBackgroundBox

@Composable
fun ManualChoosingRolePage(
    onSelectedRole: () -> Unit,
    returnToLoginPage: () -> Unit,
    modifier: Modifier = Modifier
){
    var selectedRole by remember { mutableStateOf<Int?>(null) }

    UserLoginContext.loggedUser = LoginUserData(
        userId = null,
        user_google_id = null,
        user_name = null,
        user_email = null,
        //user_password = null,
        user_roleId = null,
        user_genreId = null,
        user_contact_number = null,
        jwt = null
    )

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        RoundedBackgroundBox(fontSize = 48, contentText = "Choose your role!")

        CardWithIcons(
            iconActions = listOf(
                { run { selectedRole = UserRole.Visitor.value } },
                { run { selectedRole = UserRole.Organizer.value } },
                { run { selectedRole = UserRole.Performer.value } }
            )
        )
        if(selectedRole != null){
            when(selectedRole){
                UserRole.Visitor.value -> {
                    UserLoginContext.loggedUser!!.user_roleId = selectedRole
                    onSelectedRole()
                }
                UserRole.Organizer.value -> {
                    UserLoginContext.loggedUser!!.user_roleId = selectedRole
                    onSelectedRole()
                }
                UserRole.Performer.value -> {
                    UserLoginContext.loggedUser!!.user_roleId = selectedRole
                    onSelectedRole()
                }
            }
        }
        Row(
            modifier = modifier
                .padding(start = 40.dp, end = 40.dp)
                .fillMaxWidth()
                .align(Alignment.Start),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PreviousPageButton(onClick = { returnToLoginPage() })
        }
    }
}

@Preview
@Composable
fun ShowManualChoosingRolePage(){
    ManualChoosingRolePage(
        onSelectedRole = {},
        returnToLoginPage = {}
    )
}