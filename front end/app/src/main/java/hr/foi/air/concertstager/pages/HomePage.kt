package hr.foi.air.concertstager.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.foi.air.concertstager.core.login.LoginUserData
import hr.foi.air.concertstager.core.login.Roles.UserRole


@Composable
fun HomePage(loggedUser : LoginUserData?, onProfileClick: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome ${loggedUser!!.user_name}!")

        when(loggedUser.user_roleId){
            UserRole.Organizer.value -> {
                Text("You are logged in as organizer!")
            }
            UserRole.Performer.value -> {
                Text(text = "You are logged in as performer")
            }
            UserRole.Visitor.value -> {
                Text(text = "You are logged in as Visitor")
            }
        }

        Button(onClick = onProfileClick) {
            Text(text = "Open profile")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview(){
    val user = LoginUserData(
        userId = 1,
        user_google_id = "123",
        user_name = "Username",
        user_email = "nesto@gmail.com",
        user_roleId = 1,
        user_genreId = 1,
        user_contact_number = "0972711921",
        jwt = "2j27n24j42jj42j",
        //user_password = "password"
    )
    HomePage(loggedUser = user) {

    }
}