package hr.foi.air.concertstager.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hr.foi.air.concertstager.ui.components.ConcertCard
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hr.foi.air.concertstager.R
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.ui.components.SearchBar
import hr.foi.air.concertstager.ui.components.VisBottomNavigation
import hr.foi.air.concertstager.ui.theme.Purple
import hr.foi.air.concertstager.viewmodels.Visitor.VisitorHomepageViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import hr.foi.air.concertstager.core.login.DateFormatter
import hr.foi.air.concertstager.ws.models.response.Concert

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisitorHomePage(
    navController: NavController,
    viewModel: VisitorHomepageViewModel = viewModel()
){
    val pageTitle = viewModel.title.observeAsState().value?:""
    val upcomingConcerts by viewModel.upcomingConcerts.observeAsState()
    val finishedConcerts by viewModel.finishedConcerts.observeAsState()
    val allVenues by viewModel.venues.observeAsState()

    val searchResults by viewModel.searchResults.observeAsState()
    var firstTime = true

    viewModel.getUpcomingConcerts()
    viewModel.getFinishedConcerts()
    viewModel.getVenues()


    val onSearchClick: (String) -> Unit = { query ->
        firstTime = false
        when (pageTitle) {
            "Events" -> viewModel.searchUpcomingConcerts(query)
            "Event history" -> viewModel.searchFinishedConcerts(query)
        }
    }

    Scaffold(
        modifier = Modifier,
        bottomBar = {
            VisBottomNavigation(onButtonClick = { title ->
                when(title){
                    "Profile" -> {
                        navController.navigate("visitorProfile/${UserLoginContext.loggedUser!!.userId!!}")
                    }
                    "Event history" -> {
                        viewModel.resetSearch()
                        viewModel.title.value = title
                    }
                    "Events" -> {
                        viewModel.resetSearch()
                        viewModel.title.value = title
                    }
                }
            })
        }
    ){innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            SearchBar(
                onSearchTextChanged = onSearchClick
            )

            Text(
                text = pageTitle,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Purple,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 40.dp, bottom = 20.dp)
            )

            LazyColumn {
                when(pageTitle){
                    "Events" -> {
                        if(upcomingConcerts.isNullOrEmpty() || allVenues.isNullOrEmpty()){
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(20.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "There are no upcoming events.",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .wrapContentSize(Alignment.Center)
                                            .align(Alignment.CenterHorizontally),
                                        fontSize = 21.sp,
                                    )
                                }
                            }
                        } else {
                            if(!searchResults.isNullOrEmpty()) {
                                items(searchResults!!) { event ->
                                    val venue = allVenues?.find { it.id == event.venueId }
                                    ConcertCard(
                                        date = DateFormatter.displayDate(event.startDate.toString()),
                                        title = event.name!!,
                                        description = event.description!!,
                                        location = venue?.city ?: "Loading...",
                                        imageResource = R.drawable.concert_domu_mom,
                                        onClick = { navController.navigate("concertDetailsPage/${event.id}") },
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                }
                            }

                            else {
                                if(firstTime) {
                                    items(upcomingConcerts as List<Concert>) { event ->
                                        val venue = allVenues?.find { it.id == event.venueId }
                                        ConcertCard(
                                            date = DateFormatter.displayDate(event.startDate.toString()),
                                            title = event.name!!,
                                            description = event.description!!,
                                            location = venue?.city ?: "Loading...",
                                            imageResource = R.drawable.concert_domu_mom,
                                            onClick = { navController.navigate("concertDetailsPage/${event.id}") },
                                        )
                                        Spacer(modifier = Modifier.height(20.dp))
                                    }
                                }
                                else {
                                    item {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(20.dp),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "There is no upcoming event with that name.",
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .wrapContentSize(Alignment.Center)
                                                    .align(Alignment.CenterHorizontally),
                                                fontSize = 21.sp,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }



                    "Event history" -> {
                        if(finishedConcerts.isNullOrEmpty() || allVenues.isNullOrEmpty()){
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(20.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "There are no past events.",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .wrapContentSize(Alignment.Center)
                                            .align(Alignment.CenterHorizontally),
                                        fontSize = 21.sp,
                                    )
                                }
                            }
                        }
                        else {
                            if(!searchResults.isNullOrEmpty()) {
                                items(searchResults!!) { event ->

                                    val venue = allVenues?.find { it.id == event.venueId }
                                    ConcertCard(
                                        date = DateFormatter.displayDate(event.startDate.toString()),
                                        title = event.name!!,
                                        description = event.description!!,
                                        location = venue?.city ?: "Loading...",
                                        imageResource = R.drawable.concert_domu_mom,
                                        onClick = { navController.navigate("concertDetailsPage/${event.id}") },
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                }
                            }

                            else {
                                if(searchResults?.isEmpty() == true) {
                                    item {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(20.dp),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "There is no upcoming event with that name.",
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .wrapContentSize(Alignment.Center)
                                                    .align(Alignment.CenterHorizontally),
                                                fontSize = 21.sp,
                                            )
                                        }
                                    }
                                }
                                else {
                                    items(finishedConcerts as List<Concert>) { event ->
                                        val venue = allVenues?.find { it.id == event.venueId }
                                        ConcertCard(
                                            date = DateFormatter.displayDate(event.startDate.toString()),
                                            title = event.name!!,
                                            description = event.description!!,
                                            location = venue?.city ?: "Loading...",
                                            imageResource = R.drawable.concert_domu_mom,
                                            onClick = { navController.navigate("concertDetailsPage/${event.id}") },
                                        )
                                        Spacer(modifier = Modifier.height(20.dp))
                                    }
                                }
                            }
                        }
                    }
                }

            }

        }

    }

}



@Preview(showBackground = true)
@Composable
fun ShowVisitorHomePage(){
    VisitorHomePage(navController = rememberNavController())
}