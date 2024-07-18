package hr.foi.air.concertstager.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hr.foi.air.concertstager.R
import hr.foi.air.concertstager.ui.components.ConcertDetailsCard
import hr.foi.air.concertstager.ui.components.FinishedConcertDetailsCard
import hr.foi.air.concertstager.ui.components.PreviousPageButton
import hr.foi.air.concertstager.ui.components.VisitorReviewPopUp
import hr.foi.air.concertstager.ui.theme.Purple
import androidx.lifecycle.viewmodel.compose.viewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.DateFormatter
import hr.foi.air.concertstager.core.login.Roles.UserRole
import hr.foi.air.concertstager.ui.components.DetailsButton
import hr.foi.air.concertstager.ui.components.EventRequestsPopup
import hr.foi.air.concertstager.ui.components.OrganizerReviewPopUp
import hr.foi.air.concertstager.ui.components.PerformerReviewPopUp
import hr.foi.air.concertstager.viewmodels.Concert.ConcertDetailsViewModel
import hr.foi.air.concertstager.ws.models.response.Performer
import hr.foi.air.concertstager.ws.models.response.Review
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ConcertDetailsPage(
    id: Int,
    navController: NavController,
    viewModel: ConcertDetailsViewModel = viewModel()
){
    var isFinished by remember {mutableStateOf(false) }
    var isAllReviewed by remember { mutableStateOf(false)}
    var isReviewPopupVisible by remember { mutableStateOf(false) }
    var isEntriesPopUpVisible by remember { mutableStateOf(false)}
    val concert = viewModel.concert.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.getConcert(id)
    }

    //viewModel.getConcert(id)
    val performersForConcert by viewModel.performersForConcert.observeAsState()
    val organizer = viewModel.organizer.observeAsState()
    val venue = viewModel.venue.observeAsState()
    val reviews by viewModel.reviews.observeAsState()
    val concertEntries by viewModel.entries.observeAsState()

    LaunchedEffect(concert.value) {
        if (concert.value != null) {
            // Dohvati organizatora
            viewModel.getOrganizer(concert.value!!.userId!!)

            // Dohvati mesto održavanja
            viewModel.getVenue(concert.value!!.venueId!!)

            // Dohvati izvođače
            viewModel.getPerformersForConcert(concert.value!!.id!!)

            // Dohvati reviewe
            viewModel.getAllReviewsOfUser()

            when(UserLoginContext.loggedUser!!.user_roleId){
                UserRole.Performer.value -> {
                    //Dohvati concertEntries
                    viewModel.getConcertEntries(concert.value!!.id!!)
                }
            }
        }
    }

    if((UserLoginContext.loggedUser!!.user_roleId == UserRole.Organizer.value && concert.value != null && organizer.value != null && venue.value != null && reviews != null) || (UserLoginContext.loggedUser!!.user_roleId == UserRole.Performer.value && concert.value != null && organizer.value != null && venue.value != null && reviews != null && concertEntries != null) || (UserLoginContext.loggedUser!!.user_roleId == UserRole.Visitor.value && concert.value != null && organizer.value != null && venue.value != null && reviews != null)){
        isFinished = checkTime(concert.value!!.endDate!!)
        isAllReviewed = isAllReviewed(reviews!! ,performersForConcert!!, venue.value!!.id!!)
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(

                ) {
                    PreviousPageButton(onClick = { navController.popBackStack() })
                    Spacer(modifier = Modifier.height(25.dp))
                    Row(
                        modifier = Modifier
                            .padding(start = 20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(125.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.primary)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.concert_domu_mom),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                concert.value!!.name!!,
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp,
                                color = Purple
                            )
                            Row(
                                modifier = Modifier
                                    .padding(top = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = Purple
                                )
                                Text(text = venue.value!!.city!!,
                                    color = Purple)
                            }
                            Text(text = DateFormatter.displayDate(concert.value!!.startDate!!),
                                color = Purple)

                        }
                    }
                }
            }
            when(isFinished){
                true -> {
                    if(!isAllReviewed){
                        FinishedConcertDetailsCard(
                            performers = performersForConcert!!,
                            venue = venue.value!!.name,
                            organizer = organizer.value!!.name!!,
                            organizerContactNumber = organizer.value!!.contactNumber!!,
                            organizerMail = organizer.value!!.email!!,
                            description = concert.value!!.description!!,
                            onVenueClick = {navController.navigate("venueProfile/${venue.value!!.id}")},
                            onReviewClick = {isReviewPopupVisible = true}
                        )
                    }
                    else{
                        ConcertDetailsCard(
                            performers = performersForConcert!!,
                            venue = venue.value!!.name,
                            organizer = organizer.value!!.name!!,
                            organizerContactNumber = organizer.value!!.contactNumber!!,
                            organizerMail = organizer.value!!.email!!,
                            description = concert.value!!.description!!,
                            onVenueClick = {navController.navigate("venueProfile/${venue.value!!.id}")}
                        )
                    }
                }
                false -> {
                    if(performersForConcert!!.isNotEmpty()){
                        ConcertDetailsCard(
                            performers = performersForConcert!!,
                            venue = venue.value!!.name,
                            organizer = organizer.value!!.name!!,
                            organizerContactNumber = organizer.value!!.contactNumber!!,
                            organizerMail = organizer.value!!.email!!,
                            description = concert.value!!.description!!,
                            onVenueClick = {navController.navigate("venueProfile/${venue.value!!.id}")}
                        )
                    }
                    else{
                        ConcertDetailsCard(
                            venue = venue.value!!.name,
                            organizer = organizer.value!!.name!!,
                            organizerContactNumber = organizer.value!!.contactNumber!!,
                            organizerMail = organizer.value!!.email!!,
                            description = concert.value!!.description!!,
                            onVenueClick = {navController.navigate("venueProfile/${venue.value!!.id}")}
                        )
                    }
                }
            }
            val entry = concertEntries!!.find { it.userId == UserLoginContext.loggedUser!!.userId }
            if(UserLoginContext.loggedUser!!.user_roleId == UserRole.Performer.value && entry == null && !isFinished){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    DetailsButton(label = "Submit entry", onClick = {
                        viewModel.createConcertEntry {
                            Toast.makeText(context, "Entry submitted", Toast.LENGTH_SHORT).show()
                            navController.navigate("performerEventsPage")
                        } })
                }
            }
            if(UserLoginContext.loggedUser!!.user_roleId == UserRole.Organizer.value && !isFinished){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    DetailsButton(label = "Concert entries", onClick = {isEntriesPopUpVisible = true}
                    )
                }
            }

            if(isEntriesPopUpVisible){
                EventRequestsPopup(concert.value!!.id!!, onAccept = { isEntriesPopUpVisible = false }, onDecline = { isEntriesPopUpVisible = false }, onClosePopUp = {isEntriesPopUpVisible = false})
            }

            if (isReviewPopupVisible) {
                when(UserLoginContext.loggedUser!!.user_roleId){
                    UserRole.Visitor.value -> {
                        VisitorReviewPopUp(
                            performers = performersForConcert,
                            venue = venue.value!!,
                            onDismiss = { isReviewPopupVisible = false }
                        )
                    }
                    UserRole.Organizer.value -> {
                        OrganizerReviewPopUp(
                            performers = performersForConcert!!,
                            onDismiss = { isReviewPopupVisible = false }
                        )
                    }
                    UserRole.Performer.value -> {
                        PerformerReviewPopUp(
                            venue = venue.value!!,
                            onDismiss = { isReviewPopupVisible = false },
                            )
                    }
                }

            }
        }
    }
    else{
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            contentAlignment = Alignment.Center){
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        }
    }
}

fun isAllReviewed(reviews: List<Review>, performers: List<Performer>, venueId: Int): Boolean {
    var isAllReviewed = true
    when(UserLoginContext.loggedUser!!.user_roleId){
        UserRole.Visitor.value -> {
            for(performer in performers){
                val foundReview = reviews.find { it.userId == performer.id }
                if(foundReview == null){
                    isAllReviewed = false
                    break
                }
            }
            val venue = reviews.find { it.venueId == venueId && it.userReviewId == UserLoginContext.loggedUser!!.userId }
            if(venue == null){
                isAllReviewed = false
            }
        }
        UserRole.Organizer.value -> {
            for(performer in performers){
                val foundReview = reviews.find { it.userId == performer.id }
                if(foundReview == null){
                    isAllReviewed = false
                    break
                }
            }
        }
        UserRole.Performer.value -> {
            val venue = reviews.find { it.venueId == venueId && it.userReviewId == UserLoginContext.loggedUser!!.userId }
            if(venue == null){
                isAllReviewed = false
            }
        }
    }
    return isAllReviewed
}

fun checkTime(startDate: String): Boolean {
    val currentDateTime = LocalDateTime.now()
    val concertDateTime = LocalDateTime.parse(startDate, DateTimeFormatter.ISO_DATE_TIME)
    return currentDateTime >= concertDateTime
}

@Preview (showBackground = true)
@Composable
fun ShowConcertDetailsPage(
    modifier: Modifier = Modifier
){
    ConcertDetailsPage(1, rememberNavController())
}