package hr.foi.air.concertstager.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hr.foi.air.concertstager.R
import hr.foi.air.concertstager.ui.components.ConcertCard
import hr.foi.air.concertstager.ui.components.OrgPerBottomNavigation
import hr.foi.air.concertstager.ui.theme.OrangeLight
import hr.foi.air.concertstager.ui.theme.Purple
import hr.foi.air.concertstager.viewmodels.Organizer.OrganizerHomepageViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.DateFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrganizerEventPage(
    navController: NavController,
    viewModel: OrganizerHomepageViewModel = viewModel()
){
    var pageTitle by remember { mutableStateOf("Events") }
    val organizerConcerts by viewModel.organizerConcerts.observeAsState()
    val allVenues by viewModel.venues.observeAsState()
    //val searchResults by viewModel.searchResults.observeAsState()

    viewModel.getOrganizerConcerts()
    viewModel.getVenues()

    /*val onSearchClick: (String) -> Unit = { query ->
        viewModel.performSearch(query)
    }*/

    Scaffold(
        modifier = Modifier,
        bottomBar = {
            OrgPerBottomNavigation(onButtonClick = { title ->
                when(title){
                    "Profile" -> {
                        navController.navigate("organizerProfile/${UserLoginContext.loggedUser!!.userId}")
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
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                //.padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "My events",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Purple,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 40.dp, bottom = 20.dp, top = 20.dp)
                )
                if(organizerConcerts == null || allVenues == null){
                    CircularProgressIndicator(modifier = Modifier.size(50.dp))
                }
                else{
                    LazyColumn {
                        if(organizerConcerts.isNullOrEmpty() || allVenues.isNullOrEmpty()){
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(20.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "There are no events.",
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
                        items(organizerConcerts!!) { event ->
                            val venue = allVenues?.find { it.id == event.venueId }
                            ConcertCard(
                                date = DateFormatter.displayDate(event.startDate.toString()),
                                title = event.name!!,
                                description = event.description!!,
                                location = venue?.city ?: "Loading......",
                                imageResource = R.drawable.concert_domu_mom,
                                requestCount = 2,
                                onClick = { navController.navigate("concertDetailsPage/${event.id}") },
                                )
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .size(56.dp)
                    .background(OrangeLight, shape = androidx.compose.foundation.shape.CircleShape)
            ) {
                IconButton(
                    onClick = { navController.navigate("newEventPage") },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowOrganizerEventPage(
    modifier: Modifier = Modifier
){
    OrganizerEventPage(rememberNavController())
}