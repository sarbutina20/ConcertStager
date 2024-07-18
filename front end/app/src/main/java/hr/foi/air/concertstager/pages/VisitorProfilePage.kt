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
import hr.foi.air.concertstager.ui.components.RoundedUserBox
import hr.foi.air.concertstager.ui.components.VisitorProfileColumn
import hr.foi.air.concertstager.ui.theme.ConcertStagerTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.viewmodels.Visitor.VisitorProfileViewModel

@Composable
fun VisitorProfilePage(
    id: Int,
    navController: NavController,
    viewModel: VisitorProfileViewModel = viewModel(),
    ) {
    ConcertStagerTheme {
        LaunchedEffect(id){
            viewModel.getVisitor(id)
        }

        // Koristite novu varijablu koja će zadržati trenutne vrijednosti
        val name = viewModel.name.observeAsState().value?:""
        val email = viewModel.email.observeAsState().value?:""

        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            RoundedUserBox(
                onBack = {navController.navigate("visitorHomePage")},
                imageResource = R.drawable.default_visitor
            )

            if(UserLoginContext.loggedUser!!.userId!! == id){
                VisitorProfileColumn(
                    name = name,
                    email = email,
                    onClickEdit = { navController.navigate("editProfilePage") })
            }
            else {
                VisitorProfileColumn(
                    name = name,
                    email = email,
                    onClickEdit = { })
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun VisitorProfilePreview() {
    VisitorProfilePage(1, navController = rememberNavController())
}