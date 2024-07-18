package hr.foi.air.concertstager.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hr.foi.air.concertstager.R
import hr.foi.air.concertstager.ui.components.PerformerProfileColumn
import hr.foi.air.concertstager.ui.components.RoundedUserBox
import hr.foi.air.concertstager.ui.theme.ConcertStagerTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.ui.components.PerformerConcertHistory
import hr.foi.air.concertstager.ui.components.LinedNavigation
import hr.foi.air.concertstager.ui.components.PerformerReviews
import hr.foi.air.concertstager.viewmodels.Performer.PerformerProfilePageViewModel

@Composable
fun PerformerProfilePage(
    id: Int,
    viewModel: PerformerProfilePageViewModel = viewModel(),
    navController: NavController
    ) {
    LaunchedEffect(id){
        viewModel.getPerformer(id)
        viewModel.getPerformerReviews(id)
        viewModel.getAllUsers()
        viewModel.getPerformerConcerts(id)
        viewModel.getVenues()
    }
    ConcertStagerTheme {
        // Koristite novu varijablu koja će zadržati trenutne vrijednosti
        val name = viewModel.name.observeAsState().value ?:""
        val email = viewModel.email.observeAsState().value ?:""
        val genreId = viewModel.genreId.observeAsState().value?: 0
        val performerReviews by viewModel.performerReviews.observeAsState()
        val users by viewModel.users.observeAsState()
        val performerConcerts by viewModel.performerConcerts.observeAsState()
        val venues by viewModel.venues.observeAsState()
        val genreNames by viewModel.genreNames.observeAsState()

        var selectedTitle by remember { mutableStateOf("Reviews") }
        if(name != "" && performerReviews != null && users != null){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundedUserBox(
                    onBack = {navController.navigate("performerEventsPage")},
                    imageResource = R.drawable.default_concert_performer
                )
                if(genreId != 0){
                    viewModel.getGenre()
                    if(UserLoginContext.loggedUser!!.userId!! == id){
                        PerformerProfileColumn(
                            name = name,
                            email = email,
                            genre = genreNames,
                            onClickEdit = {
                                navController.navigate("editProfilePage")
                            }
                        )
                    }
                    else{
                        PerformerProfileColumn(
                            name = name,
                            email = email,
                            genre = genreNames,
                            onClickEdit = {
                            }
                        )
                    }

                }


                LinedNavigation(
                    titles = listOf("Reviews", "Concert history"),
                    selectedTitle = selectedTitle,
                    buttonWidth = 180.dp,
                    textSize = 17.sp,
                    onTitleSelected = {
                            title->
                        selectedTitle = title
                    }
                )

                when(selectedTitle){
                    "Reviews"->{
                        LazyColumn{
                            item { PerformerReviews(performerReviews!!, users!!) }
                        }
                    }
                    "Concert history"->{
                        LazyColumn{
                            item {PerformerConcertHistory(navController ,users!!, performerConcerts!!, venues!!)  }
                        }
                    }
                }
            }
        } else{
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
                contentAlignment = Alignment.Center){
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowPerformerProfilePage(
    modifier: Modifier = Modifier
){
    PerformerProfilePage(1, navController = rememberNavController())
}