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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hr.foi.air.concertstager.viewmodels.Venue.VenueProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import hr.foi.air.concertstager.R
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.ui.components.LinedNavigation
import hr.foi.air.concertstager.ui.components.RoundedUserBox
import hr.foi.air.concertstager.ui.components.VenueConcertHistory
import hr.foi.air.concertstager.ui.components.VenueProfileColumn
import hr.foi.air.concertstager.ui.components.VenueReviews
import hr.foi.air.concertstager.ui.theme.ConcertStagerTheme

@Composable
fun VenueProfilePage(
    id: Int,
    viewModel: VenueProfileViewModel = viewModel(),
    navController: NavController
) {
    LaunchedEffect(id){
        viewModel.getVenue(id)
        viewModel.getVenueReviews(id)
        viewModel.getAllUsers()
        viewModel.getVenueConcerts(id)
    }
    ConcertStagerTheme {
        // Koristite novu varijablu koja će zadržati trenutne vrijednosti
        val name = viewModel.name.observeAsState().value ?:""
        val description = viewModel.description.observeAsState().value ?:""
        val city = viewModel.city.observeAsState().value ?:""
        val address = viewModel.address.observeAsState().value?: ""
        val capacity = viewModel.capacity.observeAsState().value?: 0
        val userId = viewModel.userId.observeAsState().value?:0

        val venueReviews by viewModel.venueReviews.observeAsState()
        val venueConcerts by viewModel.venueConcerts.observeAsState()
        val users by viewModel.users.observeAsState()

        var selectedTitle by remember { mutableStateOf("Reviews") }
        if(name != "" && venueReviews != null && users != null && venueConcerts != null){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RoundedUserBox(
                    onBack = {navController.popBackStack()},
                    imageResource = R.drawable.default_concert_performer
                )
                    if(UserLoginContext.loggedUser!!.userId!! == userId){
                        VenueProfileColumn(
                            name = name,
                            description = description,
                            city = city,
                            address = address,
                            capacity = capacity
                        )
                    }
                    else{
                        VenueProfileColumn(
                            name = name,
                            description = description,
                            city = city,
                            address = address,
                            capacity = capacity
                        )
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
                            item { VenueReviews(venueReviews!!, users!!) }
                        }
                    }
                    "Concert history"->{
                        LazyColumn{
                            item { VenueConcertHistory(navController ,users!!, venueConcerts!!, city)  }
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