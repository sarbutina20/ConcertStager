package hr.foi.air.concertstager.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hr.foi.air.concertstager.R
import hr.foi.air.concertstager.core.login.DateFormatter
import hr.foi.air.concertstager.ws.models.response.Concert
import hr.foi.air.concertstager.ws.models.response.PerformerReview
import hr.foi.air.concertstager.ws.models.response.User
import hr.foi.air.concertstager.ws.models.response.Venue
import hr.foi.air.concertstager.ws.models.response.VenueReview

@Composable
fun PerformerConcertHistory(navController: NavController, users: List<User>, performerConcerts: List<Concert>, venues : List<Venue>) {
    var showMorePopup by remember { mutableStateOf(false) }
    val eventList = performerConcerts
    eventList.take(3).forEach {event->
        val venue = venues.find { it.id == event.venueId }
        ConcertCard(
            onClick = { navController.navigate("concertDetailsPage/${event.id}") },
            date = DateFormatter.displayDate(event.startDate.toString()),
            title = event.name!!,
            description = event.description!!,
            location = venue!!.city!!,
            imageResource = R.drawable.default_concert_album
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
    if (eventList.size > 3) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 40.dp),
            horizontalArrangement = Arrangement.End
        ){
            Button(
                modifier = Modifier,
                onClick = { showMorePopup = true }) {
                Text("More")
            }

        }
    }

    if (showMorePopup) {
        MorePerformerConcertsPopup(
            navController,
            venues,
            events = eventList,
            onClosePopup = { showMorePopup = false })
    }

}

@Composable
fun VenueConcertHistory(navController: NavController, users: List<User>, venueConcerts: List<Concert>, venueCity: String) {
    var showMorePopup by remember { mutableStateOf(false) }
    val eventList = venueConcerts
    eventList.take(3).forEach {event->
        ConcertCard(
            onClick = { navController.navigate("concertDetailsPage/${event.id}") },
            date = DateFormatter.displayDate(event.startDate.toString()),
            title = event.name!!,
            description = event.description!!,
            location = venueCity,
            imageResource = R.drawable.default_concert_album
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
    if (eventList.size > 3) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 40.dp),
            horizontalArrangement = Arrangement.End
        ){
            Button(
                modifier = Modifier,
                onClick = { showMorePopup = true }) {
                Text("More")
            }

        }
    }

    if (showMorePopup) {
        MoreVenueConcertsPopup(
            navController,
            venueCity,
            events = eventList,
            onClosePopup = { showMorePopup = false })
    }

}

@Composable
fun PerformerReviews(performerReviews: List<PerformerReview>, users: List<User>) {
    val reviews = mutableListOf<Int>()
    for (review in performerReviews){
        reviews.add(review.grade!!)
    }
    val reviewCards = performerReviews

    var showMorePopup by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ReviewGraph(reviews = reviews)
    }
    reviewCards.take(3).forEach { review ->
        val username = users.find { it.id == review.userReviewId }
        if(username != null){
            ReviewCard(
                username = username.name!!,
                grade = review.grade.toString(),
                comment = review.description!!)
        }
    }

    if (reviewCards.size > 3) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 40.dp),
            horizontalArrangement = Arrangement.End
        ){
            Button(
                modifier = Modifier,
                onClick = { showMorePopup = true }) {
                Text("More")
            }

        }
    }

    if (showMorePopup) {
        MorePerformerReviewsPopup(
            users,
            reviews = reviewCards,
            onClosePopup = { showMorePopup = false }
        )
    }
}

@Composable
fun VenueReviews(venueReviews: List<VenueReview>, users: List<User>) {
    val reviews = mutableListOf<Int>()
    for (review in venueReviews){
        reviews.add(review.grade!!)
    }
    val reviewCards = venueReviews

    var showMorePopup by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ReviewGraph(reviews = reviews)
    }
    reviewCards.take(3).forEach { review ->
        val username = users.find { it.id == review.userReviewId }
        if(username != null){
            ReviewCard(
                username = username.name!!,
                grade = review.grade.toString(),
                comment = review.description!!
            )
        }
    }

    if (reviewCards.size > 3) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 40.dp),
            horizontalArrangement = Arrangement.End
        ){
            Button(
                modifier = Modifier,
                onClick = { showMorePopup = true }) {
                Text("More")
            }

        }
    }

    if (showMorePopup) {
        MoreVenueReviewsPopup(
            users,
            reviews = reviewCards,
            onClosePopup = { showMorePopup = false }
        )
    }
}