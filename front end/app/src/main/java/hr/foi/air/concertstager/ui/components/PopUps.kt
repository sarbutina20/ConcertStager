package hr.foi.air.concertstager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import hr.foi.air.concertstager.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewModelScope
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.ws.models.PerformerReviewCreateBody
import hr.foi.air.concertstager.ws.models.response.Performer
import hr.foi.air.concertstager.ws.models.response.Venue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hr.foi.air.concertstager.core.login.DateFormatter
import hr.foi.air.concertstager.viewmodels.ConcertEntry.ConcertEntryRequestPopUpViewModel
import hr.foi.air.concertstager.viewmodels.Organizer.OrganizerReviewPopUpViewModel
import hr.foi.air.concertstager.viewmodels.Performer.PerformerReviewPopUpViewModel
import hr.foi.air.concertstager.viewmodels.Visitor.VisitorReviewPopUpViewModel
import hr.foi.air.concertstager.ws.models.ConcertEntryUpdateBody
import hr.foi.air.concertstager.ws.models.VenueReviewCreateBody
import hr.foi.air.concertstager.ws.models.response.Concert
import hr.foi.air.concertstager.ws.models.response.PerformerReview
import hr.foi.air.concertstager.ws.models.response.User
import hr.foi.air.concertstager.ws.models.response.VenueReview
import kotlinx.coroutines.launch

@Composable
fun MorePerformerReviewsPopup(
    users: List<User>,
    reviews: List<PerformerReview>,
    onClosePopup: () -> Unit
) {
    Dialog(
        onDismissRequest = onClosePopup,
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(top = 40.dp)
                .fillMaxWidth()
        ){
            UnderlinedText(onButtonClick = {},label = "All reviews")
            Box(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                LazyColumn {
                    items(reviews) { review ->
                        val username = users.find { it.id == review.userReviewId }
                        ReviewCard(
                            username = username!!.name!!,
                            grade = review.grade.toString(),
                            comment = review.description!!
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MoreVenueReviewsPopup(
    users: List<User>,
    reviews: List<VenueReview>,
    onClosePopup: () -> Unit
) {
    Dialog(
        onDismissRequest = onClosePopup,
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(top = 40.dp)
                .fillMaxWidth()
        ){
            UnderlinedText(onButtonClick = {},label = "All reviews")
            Box(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                LazyColumn {
                    items(reviews) { review ->
                        val username = users.find { it.id == review.userReviewId }
                        ReviewCard(
                            username = username!!.name!!,
                            grade = review.grade.toString(),
                            comment = review.description!!
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisitorReviewPopUp(
    onDismiss: () -> Unit,
    performers:List<Performer>?,
    venue: Venue? = null,
    viewModel: VisitorReviewPopUpViewModel = viewModel()
) {
    var selectedPerformer by remember { mutableStateOf<Performer?>(null)}
    var selectedRating by remember { mutableIntStateOf(1) }
    var selectedItem by remember { mutableStateOf("Performer")}
    val comment = viewModel.comment.observeAsState().value?:""
    val errorMessage = viewModel.errorMessage.observeAsState().value?:""

    val showPopup = viewModel.popup.observeAsState()

    if (showPopup.value!!) {
        if(performers!!.isNotEmpty()){
            selectedPerformer = performers[0]
        }
        AlertDialog(
            onDismissRequest = {viewModel.popup.value = false},
            title = {
                Text("Give Review")
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if(errorMessage.isNotEmpty()){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = errorMessage, color = Color.Red)
                        }
                    }
                    StyledComboBox(
                        label = "",
                        items = listOf("Performer", "Venue"),
                        onItemSelected = {selectedItem = it},
                        selectedItem = selectedItem)
                    if(selectedItem == "Performer" && performers.isNotEmpty()){
                        performers.let {
                            PerformerNavigation(
                                performers = performers,
                                selectedPerformer = selectedPerformer,
                                onPerformerSelected = { performer ->
                                    selectedPerformer = performer
                                }
                            )

                        }
                    }
                    // Navigation for performers
                    if(selectedItem == "Venue"){
                        venue?.let{
                            Text(
                                text = venue.name!!,
                                modifier = Modifier
                                    .padding(4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    // Rating stars
                    RatingBar(selectedRating) { newRating -> selectedRating = newRating }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Comment text field
                    TextField(
                        value = comment,
                        onValueChange = { viewModel.comment.value = it },
                        label = { Text("Leave a comment") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.errorMessage.value = ""
                        viewModel.viewModelScope.launch {
                            try {
                                when(selectedItem){
                                    "Performer" -> {
                                        val performerReviewCreateBody = PerformerReviewCreateBody(
                                            grade = selectedRating,
                                            description = comment,
                                            userReviewId = UserLoginContext.loggedUser!!.userId,
                                            userId = selectedPerformer!!.id
                                        )
                                        viewModel.reviewPerformer(performerReviewCreateBody)
                                    }
                                    "Venue" -> {
                                        val venueReviewCreateBody = VenueReviewCreateBody(
                                            grade = selectedRating,
                                            description = comment,
                                            userReviewId = UserLoginContext.loggedUser!!.userId,
                                            venueId = venue!!.id
                                        )
                                        viewModel.reviewVenue(venueReviewCreateBody)
                                    }
                                }
                            } catch(e: Exception){
                                viewModel.errorMessage.value = e.message ?: "Unknown error."
                            }
                        }
                    }
                ) {
                    Text("Submit")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        viewModel.errorMessage.value = ""
                        viewModel.comment.value = ""
                        selectedRating = 1
                        onDismiss()
                        viewModel.popup.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerformerReviewPopUp(
    onDismiss: () -> Unit,
    viewModel: PerformerReviewPopUpViewModel = viewModel(),
    venue: Venue? = null
) {
    var selectedRating by remember { mutableIntStateOf(1) }
    val comment = viewModel.comment.observeAsState().value?:""

    val errorMessage = viewModel.errorMessage.observeAsState().value?:""

    val showPopup = viewModel.popup.observeAsState()

    if (showPopup.value!!) {
        AlertDialog(
            onDismissRequest = { viewModel.popup.value = false },
            title = {
                Text("Give Review")
            },

            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if(errorMessage.isNotEmpty()){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = errorMessage, color = Color.Red)
                        }
                    }
                    StyledComboBox(
                        label = "",
                        items = listOf("Venue"),
                        onItemSelected = {},
                        selectedItem = "Venue")
                    // Navigation for performers
                    venue?.let{
                        Text(
                            text = venue.name!!,
                            modifier = Modifier
                                .padding(4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Rating stars
                    RatingBar(selectedRating, { newRating -> selectedRating = newRating })

                    Spacer(modifier = Modifier.height(8.dp))

                    // Comment text field
                    TextField(
                        value = comment,
                        onValueChange = { viewModel.comment.value = it },
                        label = { Text("Leave a comment") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.errorMessage.value = ""
                        viewModel.viewModelScope.launch {
                            try {
                                val venueReviewCreateBody = VenueReviewCreateBody(
                                    grade = selectedRating,
                                    description = comment,
                                    userReviewId = UserLoginContext.loggedUser!!.userId,
                                    venueId = venue!!.id
                                )
                                viewModel.reviewVenue(venueReviewCreateBody)
                            } catch (e: Exception){
                                viewModel.errorMessage.value = e.message
                            }
                        }
                    }
                ) {
                    Text("Submit")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        viewModel.errorMessage.value = ""
                        viewModel.comment.value = ""
                        selectedRating = 1
                        onDismiss()
                        viewModel.popup.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrganizerReviewPopUp(
    onDismiss: () -> Unit,
    viewModel: OrganizerReviewPopUpViewModel = viewModel(),
    performers:List<Performer>? = null
) {
    var selectedPerformer by remember { mutableStateOf<Performer?>(null)}
    var selectedRating by remember { mutableIntStateOf(1) }
    val comment = viewModel.comment.observeAsState().value?:""
    val errorMessage = viewModel.errorMessage.observeAsState().value?:""

    val showPopup = viewModel.popup.observeAsState()

    if (showPopup.value!!) {
        if(performers!!.isNotEmpty()){
            selectedPerformer = performers.get(0)
        }
        AlertDialog(
            onDismissRequest = { viewModel.popup.value = false },
            title = {
                Text("Give Review")
            },

            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if(errorMessage.isNotEmpty()){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = errorMessage, color = Color.Red)
                        }
                    }
                    StyledComboBox(
                        label = "",
                        items = listOf("Performer"),
                        onItemSelected = {},
                        selectedItem = "Performer")
                    // Navigation for performers
                    performers.let {
                        PerformerNavigation(
                            performers = performers,
                            selectedPerformer = selectedPerformer,
                            onPerformerSelected = { performer ->
                                selectedPerformer = performer
                            }
                        )

                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // Rating stars
                    RatingBar(selectedRating, { newRating -> selectedRating = newRating })

                    Spacer(modifier = Modifier.height(8.dp))

                    // Comment text field
                    TextField(
                        value = comment,
                        onValueChange = { viewModel.comment.value = it },
                        label = { Text("Leave a comment") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.errorMessage.value = ""
                        viewModel.viewModelScope.launch {
                            try {
                                val performerReviewCreateBody = PerformerReviewCreateBody(
                                    grade = selectedRating,
                                    description = comment,
                                    userReviewId = UserLoginContext.loggedUser!!.userId,
                                    userId = selectedPerformer!!.id
                                )
                                viewModel.reviewPerformer(performerReviewCreateBody)
                            } catch (e: Exception){
                                viewModel.errorMessage.value = e.message
                            }
                        }
                    }
                ) {
                    Text("Submit")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        viewModel.errorMessage.value = ""
                        viewModel.comment.value = ""
                        selectedRating = 1
                        onDismiss()
                        viewModel.popup.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}



@Composable
fun PerformerNavigation(
    performers: List<Performer>?,
    selectedPerformer: Performer?,
    onPerformerSelected: (Performer) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        //horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        performers!!.forEach { performer ->
            PerformerButton(
                name = performer.name!!,
                isSelected = performer == selectedPerformer,
                onClick = { onPerformerSelected(performer) }
            )
        }
    }
}

@Composable
fun PerformerButton(
    name: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .height(48.dp)
            .width(70.dp)
    ) {
        Text(
            text = name,
            modifier = Modifier
                .padding(bottom = if (isSelected) 4.dp else 0.dp)
                //.background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                .padding(4.dp)
        )
        Box(
            modifier = Modifier
                .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Black)
                .height(if (isSelected) 3.dp else 1.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)

        )
    }
}

@Composable
fun RatingBar(
    selectedRating: Int,
    onRatingChanged: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        (1..5).forEach { rating ->
            if (rating <= selectedRating) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier.clickable { onRatingChanged(rating) })
            }
            else{
                Icon(
                    painter = painterResource(id = R.drawable.star_border),
                    contentDescription = null,
                    modifier =
                    Modifier
                        .clickable { onRatingChanged(rating) }
                        .width(24.dp)
                        .height(24.dp)
                )
            }
        }
    }
}

@Composable
fun MorePerformerConcertsPopup(
    navController: NavController,
    venues: List<Venue>,
    events: List<Concert>,
    onClosePopup: () -> Unit,
) {
    Dialog(
        onDismissRequest = onClosePopup,
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
        ){
            UnderlinedText(onButtonClick = {},label = "All event history")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LazyColumn {
                    items(events) { event ->
                        val venue = venues.find { it.id == event.venueId }
                        ConcertCard(
                            onClick = { navController.navigate("concertDetailsPage/${event.id}") },
                            date =  DateFormatter.displayDate(event.startDate.toString()),
                            title = event.name!!,
                            description = event.description!!,
                            location = venue!!.city!!,
                            imageResource = R.drawable.default_concert_album
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun MoreVenueConcertsPopup(
    navController: NavController,
    venueName: String,
    events: List<Concert>,
    onClosePopup: () -> Unit,
) {
    Dialog(
        onDismissRequest = onClosePopup,
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
        ){
            UnderlinedText(onButtonClick = {},label = "All event history")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LazyColumn {
                    items(events) { event ->
                        ConcertCard(
                            onClick = { navController.navigate("concertDetailsPage/${event.id}") },
                            date =  DateFormatter.displayDate(event.startDate.toString()),
                            title = event.name!!,
                            description = event.description!!,
                            location = venueName,
                            imageResource = R.drawable.default_concert_album
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun EventRequestsPopup(
    concertId: Int,
    onAccept: () -> Unit,
    onDecline: () -> Unit,
    onClosePopUp: () -> Unit,
    viewModel: ConcertEntryRequestPopUpViewModel = viewModel()
) {
    val concertEntries by viewModel.unresolvedEntries.observeAsState()
    val allPerformers by viewModel.allPerformers.observeAsState()

    LaunchedEffect(true) {
        viewModel.getUnresolvedEntries(concertId)
        viewModel.getAllPerformers()
    }

    if(concertEntries != null && allPerformers != null){
        Dialog(
            onDismissRequest = onClosePopUp,
            properties = DialogProperties(usePlatformDefaultWidth = false),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                LazyColumn {
                    items(concertEntries!!) { concertEntry ->
                        val performer = allPerformers!!.find { it.id == concertEntry.userId }
                        if(performer != null){
                            UserInvitationCard(
                                username = performer.name!!,
                                onAccept = { val concertEntryUpdateBody = ConcertEntryUpdateBody(
                                    isAccepted = true
                                )
                                    viewModel.acceptDenyEntry(concertEntry.id!!, concertEntryUpdateBody)
                                    onAccept()},
                                onDecline = { val concertEntryUpdateBody = ConcertEntryUpdateBody(
                                    isAccepted = false
                                )
                                    viewModel.acceptDenyEntry(concertEntry.id!!, concertEntryUpdateBody)
                                    onDecline() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onClosePopUp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                    ) {
                        Text(text = "Close")
                    }
                }
            }
        }
    } else{
        CircularProgressIndicator(modifier = Modifier.size(50.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun ShowPopUps(
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .size(75.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){

        //EventRequestsPopup(usernames = listOf("Ivan"), onAccept = {}, onDecline = {})
    }
}