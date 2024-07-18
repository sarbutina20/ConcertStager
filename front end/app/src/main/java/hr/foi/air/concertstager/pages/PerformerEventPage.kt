package hr.foi.air.concertstager.pages


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hr.foi.air.concertstager.R
import hr.foi.air.concertstager.ui.components.ConcertCard
import hr.foi.air.concertstager.ui.components.LinedNavigation
import hr.foi.air.concertstager.ui.components.OrgPerBottomNavigation
import hr.foi.air.concertstager.ui.components.SearchBar
import hr.foi.air.concertstager.ui.components.UnderlinedText
import androidx.lifecycle.viewmodel.compose.viewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.DateFormatter
import hr.foi.air.concertstager.viewmodels.Performer.PerformerHomepageViewModel
import hr.foi.air.concertstager.ws.models.response.Concert
import hr.foi.air.concertstager.ws.models.response.Venue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerformerEventPage(
    navController: NavController,
    viewModel: PerformerHomepageViewModel = viewModel(),
){
    val selectedTitle = viewModel.selectedTitle.observeAsState().value?:""
    var pageTitle by remember { mutableStateOf("Events") }
    val upcomingConcerts by viewModel.upcomingConcerts.observeAsState()
    val acceptedConcerts by viewModel.acceptedConcerts.observeAsState()
    val pendingConcerts by viewModel.pendingConcerts.observeAsState()
    val allVenues by viewModel.venues.observeAsState()


    viewModel.getUpcomingConcerts()
    viewModel.getAcceptedConcertsForPerformer()
    viewModel.getPendingConcertsForPerformer()
    viewModel.getVenues()

    val onSearchClick: (String) -> Unit = { query ->
        viewModel.performSearch(query)
    }

    Scaffold(
        modifier =Modifier,
        bottomBar = {
            OrgPerBottomNavigation(onButtonClick = { title ->
                when(title){
                    "Profile" -> {
                        navController.navigate("performerProfile/${UserLoginContext.loggedUser!!.userId}")
                    }
                    "Events" -> {
                        pageTitle = title
                    }
                    "Chat" -> {
                        navController.navigate("listOfGroupsPage")
                    }
                }
            })
        }
    ){innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            SearchBar(
                onSearchTextChanged = onSearchClick
            )

            LinedNavigation(
                buttonWidth = 180.dp,
                textSize = 17.sp,
                titles = listOf("Events", "My Events"),
                selectedTitle = selectedTitle,
                onTitleSelected = { viewModel.selectedTitle.value = it
                })


            Spacer(modifier = Modifier.height(20.dp))

            when (selectedTitle) {
                "Events" -> {
                    if(upcomingConcerts == null || allVenues == null){
                        CircularProgressIndicator(modifier = Modifier.size(50.dp))
                    }
                    else{
                        Events(navController, upcomingConcerts!!, allVenues!!, viewModel)
                    }
                }
                "My Events" -> {
                    if(acceptedConcerts == null || pendingConcerts == null || allVenues == null){
                        CircularProgressIndicator(modifier = Modifier.size(50.dp))
                    }
                    else{
                        MyEvents(navController, acceptedConcerts!!, pendingConcerts!!, allVenues!!)
                    }
                }
            }
        }

    }

}

@Composable
fun Events(
    navController: NavController,
    upcomingConcerts: List<Concert>,
    allVenues: List<Venue>,
    viewModel: PerformerHomepageViewModel
    ){

    val searchResults by viewModel.searchResults.observeAsState()

    LazyColumn {
        if(upcomingConcerts.isEmpty() || allVenues.isEmpty()){
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
                    val venue = allVenues.find { it.id == event.venueId }
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
                    items(upcomingConcerts) { event ->
                        val venue = allVenues.find { it.id == event.venueId }
                        ConcertCard(
                            date = DateFormatter.displayDate(event.startDate.toString()),
                            title = event.name!!,
                            description = event.description!!,
                            location = venue?.city ?: "Loading.....",
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


@Composable
fun MyEvents(
    navController: NavController,
    acceptedConcerts: List<Concert>,
    pendingConcerts: List<Concert>,
    allVenues: List<Venue>
){
    var isExpandedAccepted by remember { mutableStateOf(false) }

    val lazyColumnState = rememberLazyListState()


    LazyColumn(
        state = lazyColumnState
    )
    {
        item{UnderlinedText(
            label = "Accepted",
            onButtonClick = {
                isExpandedAccepted = !isExpandedAccepted
            })
        }
        item{Spacer(modifier = Modifier.height(20.dp))}

        items(acceptedConcerts) { event ->
            val venue = allVenues.find { it.id == event.venueId }
            ConcertCard(
                date = DateFormatter.displayDate(event.startDate.toString()),
                title = event.name!!,
                description = event.description!!,
                location = venue?.city ?: "Loading.........",
                imageResource = R.drawable.concert_domu_mom,
                onClick = { navController.navigate("concertDetailsPage/${event.id}") },
            )
            Spacer(modifier = Modifier.height(20.dp))
        }


        item{UnderlinedText(
            label = "Pending",
            onButtonClick = {})
        }
        item{Spacer(modifier = Modifier.height(20.dp))}
        items(pendingConcerts) { event ->
            val venue = allVenues.find { it.id == event.venueId }
            ConcertCard(
                date = DateFormatter.displayDate(event.startDate.toString()),
                title = event.name!!,
                description = event.description!!,
                location = venue?.city ?: "Loading........",
                imageResource = R.drawable.concert_domu_mom,
                onClick = { navController.navigate("concertDetailsPage/${event.id}") },
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
@Preview
@Composable
fun ShowPerformerEventPage(
    modifier: Modifier = Modifier
){
    PerformerEventPage(rememberNavController())
}