package hr.foi.air.concertstager.ui.components

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import hr.foi.air.concertstager.R
import hr.foi.air.concertstager.ui.theme.BlueLight
import hr.foi.air.concertstager.ui.theme.OrangeLight
import hr.foi.air.concertstager.ui.theme.Purple
import hr.foi.air.concertstager.ws.models.response.Performer
import java.text.SimpleDateFormat
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

@Composable
fun AlbumCard(
    albumName: String,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .height(106.dp)
            .width(96.dp),
        colors = CardDefaults.cardColors(OrangeLight)
    ){
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.default_concert_album),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .clip(shape = MaterialTheme.shapes.medium)
            )
            Text(
                text = albumName,
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )
        }

    }
}

@Composable
fun ReviewCard(
    username: String,
    grade: String,
    comment: String,
    modifier: Modifier = Modifier
) {
    val profilePictureSize = 48.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
    ) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            // Profile Picture
            Box(
                modifier = Modifier
                    .size(profilePictureSize)
                    .clip(CircleShape)
                    .background(BlueLight)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // User Info and Rating
            Column {
                // User Info (Username)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(username, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                            .padding(4.dp)
                    )
                    Text(grade) // Replace with actual rating
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Comment
                Text(
                    text = comment,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = modifier.height(7.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun ConcertCard(
    onClick: () ->Unit,
    date: String,
    title: String,
    description: String,
    location: String,
    imageResource: Int,
    requestCount: Int =0,
    modifier: Modifier= Modifier
) {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    Column(
        modifier = modifier,
        //.padding(top = if (requestCount > 0) 180.dp else 0.dp),
        verticalArrangement = Arrangement.spacedBy((-10).dp),
    ) {
        if (requestCount > 0) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp)
                    .zIndex(1f),
                contentAlignment = Alignment.TopEnd
            ) {
                Badge(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    containerColor = OrangeLight,
                    content = {
                        Text(
                            text = requestCount.toString(),
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            }
        }
        Card(
            modifier = modifier
                .height(175.dp)
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp)
                .clickable { onClick() },
            colors = CardDefaults.cardColors(Purple)
        ) {
            Row {
                // Left side
                Box(
                    modifier = modifier
                        .fillMaxHeight()
                        .padding(16.dp)
                        .weight(2f)
                ) {
                    Column(
                        modifier = modifier
                            .padding(top = 12.dp)

                    ) {
                        Text(
                            text = date,
                            fontSize = 11.sp,
                            color = Color.White
                        )
                        Text(
                            text = title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(modifier = modifier.height(4.dp))
                        Divider(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primary)
                                .height(2.dp)
                        )
                        Spacer(modifier = modifier.height(4.dp))
                        Text(
                            text = description,
                            color = Color.White,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 12.sp
                        )

                    }
                    Column(
                        modifier = modifier
                            .align(Alignment.BottomCenter)
                    ) {
                        Divider(
                            modifier = modifier
                                .background(OrangeLight)
                                .height(2.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Text(
                                text = location,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }

                    }

                }

                // Right side
                Box(
                    modifier = modifier
                        .fillMaxHeight()
                        .aspectRatio(0.7f)

                ) {
                    Image(
                        painter = painterResource(id = imageResource),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .size(110.dp)
                            .clip(shape = MaterialTheme.shapes.medium)
                            .align(Alignment.CenterEnd)
                            .padding(end = 10.dp)
                            .border(1.dp, Color.Black, shape = MaterialTheme.shapes.medium)
                    )
                }
            }
        }
    }
}

@Composable
fun GroupCard(
    groupName: String,
    newMessagesCount: Int = 0,
    shape: androidx.compose.ui.graphics.Shape,
    modifier: Modifier = Modifier,
    check : Boolean = newMessagesCount > 0,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(bottom = 10.dp),
        shape = shape,
        colors = CardDefaults.cardColors(Purple),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Group image in a circle
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(BlueLight)
            ) {
                androidx.compose.foundation.Image(
                    painter = painterResource(id = R.drawable.group_chat_icon),
                    contentDescription = null)
            }

            Text(
                text = groupName,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 16.sp
                ),
                color = Color.White,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            )
            if (check) {
                Box(
                    modifier = modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(OrangeLight)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$newMessagesCount",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun UserInvitationCard(
    username: String,
    onAccept: () -> Unit,
    onDecline: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(Purple),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Profile Picture and Username Row
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // You can replace the placeholder icon with the actual profile icon
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(BlueLight)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = username,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Accept and Decline Buttons Row with Icons
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { onAccept() },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White)
                }

                IconButton(
                    onClick = { onDecline() },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = Color.White)
                }
            }
        }
    }
}




@Composable
fun ConcertDetailsCard(
    performers:List<Performer> ? = null,
    venue: String ? = null,
    organizer: String,
    organizerContactNumber: String,
    organizerMail: String,
    description: String? = null,
    onVenueClick: () -> Unit = {},
    modifier: Modifier = Modifier
){
    Card(
        colors = CardDefaults.cardColors(Purple),
        modifier = modifier
            .padding(start = 30.dp, end = 30.dp)
    ){
        Column(

        ) {
            performers?.let{
                Text(
                    text = "Performers:",
                    color = Color.White,
                    modifier = modifier
                        .padding(10.dp)
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(10.dp)
                ) {
                    items(performers) { performer ->
                        ClickableTextLabel(
                            text = performer.name!!,
                            modifier = modifier.padding(end = 4.dp)
                        )
                    }
                }
            }
            venue?.let{
                Text(
                    text = "Venue:",
                    color = Color.White,
                    modifier = modifier
                        .padding(10.dp)
                )
                ClickableTextLabel(
                    text = venue,
                    onClick = onVenueClick,
                    modifier = modifier.padding(start= 10.dp, end = 10.dp)
                )
            }
            Text(
                text = "Contact:",
                color = Color.White,
                modifier = modifier
                    .padding(10.dp)
            )
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = CardDefaults.cardColors(Purple)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Spacer(modifier = modifier.width(4.dp))
                    ClickableTextLabel(
                        text = organizer,
                        modifier = modifier.padding(start= 10.dp, end = 10.dp)
                    )
                    LabelWithIcon(content = organizerContactNumber,
                        imageVector = Icons.Default.Call)
                    LabelWithIcon(content = organizerMail,
                        imageVector = Icons.Default.MailOutline)
                }
            }
            description?.let{
                Text(
                    text = "Description:",
                    color = Color.White,
                    modifier = modifier
                        .padding(10.dp)
                )
                Text(
                    text = description,
                    color = Color.White,
                    modifier = modifier
                        .padding(start =10.dp, end = 10.dp, bottom = 10.dp),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun FinishedConcertDetailsCard(
    performers:List<Performer>,
    venue: String ? = null,
    organizer: String,
    organizerContactNumber: String,
    organizerMail: String,
    description: String? = null,
    onVenueClick: () -> Unit,
    onReviewClick: () ->Unit,
    modifier: Modifier = Modifier
){
    Card(
        colors = CardDefaults.cardColors(Purple),
        modifier = modifier
            .padding(start = 30.dp, end = 30.dp)
    ){
        Column(

        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.Start),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Performers:",
                    color = Color.White,
                    modifier = modifier
                        .padding(10.dp)
                )
                SmallButtonWithIcon(
                    label = "Give review",
                    imageVector = Icons.Default.Star,
                    onClick = onReviewClick,
                    modifier=modifier
                    //.padding(end = 10.dp)
                )
            }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp)
            ) {
                items(performers) { performer ->
                    ClickableTextLabel(
                        text = performer.name!!,
                        modifier = modifier.padding(end = 4.dp)
                    )
                }
            }

            venue?.let{
                Text(
                    text = "Venue:",
                    color = Color.White,
                    modifier = modifier
                        .padding(10.dp)
                )
                ClickableTextLabel(
                    text = venue,
                    onClick = onVenueClick,
                    modifier = modifier.padding(start= 10.dp, end = 10.dp)
                )
            }
            Text(
                text = "Contact:",
                color = Color.White,
                modifier = modifier
                    .padding(10.dp)
            )
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = CardDefaults.cardColors(Purple)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Spacer(modifier = modifier.width(4.dp))
                    ClickableTextLabel(
                        text = organizer,
                        modifier = modifier.padding(start= 10.dp, end = 10.dp)
                    )
                    LabelWithIcon(content = organizerContactNumber,
                        imageVector = Icons.Default.Call)
                    LabelWithIcon(content = organizerMail,
                        imageVector = Icons.Default.MailOutline)
                }
            }
            description?.let{
                Text(
                    text = "Description:",
                    color = Color.White,
                    modifier = modifier
                        .padding(10.dp)
                )
                Text(
                    text = description,
                    color = Color.White,
                    modifier = modifier
                        .padding(start =10.dp, end = 10.dp, bottom = 10.dp),
                    fontSize = 12.sp
                )
            }
        }
    }
}


@Composable
fun GroupChatCard(
    groupName: String,
    newMessagesCount: Int = 0,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
){
    GroupCard(
        groupName = groupName,
        newMessagesCount = newMessagesCount,
        shape = RoundedCornerShape(16.dp),
        check = newMessagesCount > 0,
        onClick = onClick
    )
}

@Composable
fun GroupMainCard(
    groupName: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
){
    GroupCard(
        groupName = groupName,
        shape = RectangleShape,
        check = false,
        onClick = onClick
    )
}


@Preview
@Composable
fun ShowCards(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .size(75.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        AlbumCard(albumName = "Treba mi nesto jace od sna")
        GroupChatCard(
            groupName = "Optimisti",
            newMessagesCount = 2
        )
        GroupMainCard(
            groupName = "Najjaca grupa")
        UserInvitationCard(username = "Ivan", onAccept = { /*TODO*/ }, onDecline = { /*TODO*/ })
    }
}
