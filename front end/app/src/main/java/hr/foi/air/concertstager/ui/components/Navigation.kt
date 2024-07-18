package hr.foi.air.concertstager.ui.components

import android.media.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hr.foi.air.concertstager.R
import hr.foi.air.concertstager.ui.theme.Purple

@Composable
fun LinedNavigation(
    buttonWidth: Dp,
    textSize: TextUnit,
    titles: List<String>,
    selectedTitle: String?,
    onTitleSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 40.dp),
    ) {
        titles.forEach { title ->
            TitleButton(
                name = title,
                isSelected = title == selectedTitle,
                onClick = { onTitleSelected(title) }
            )
        }
    }
}
@Composable
fun NavBar(
    modifier: Modifier = Modifier,
    images: List<Int>,
    labels: List<String>
) {
    BottomAppBar(
        modifier = modifier,
        containerColor = Purple,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in images.indices) {
                IconWithText(
                    imageResorce = images[i],
                    label = labels[i],
                    isCenter = i == images.size / 2
                )
            }
        }
    }
}

@Composable
fun IconWithText(imageResorce: Int, label: String, isCenter: Boolean) {
    Column(
        modifier = Modifier
            .clip(if (isCenter) CircleShape else RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.primary)
            .size(if (isCenter) 100.dp else 75.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.foundation.Image(
            painter = painterResource(id = imageResorce),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(8.dp)
        )
        Text(
            text = label,
            fontSize = 10.sp,
            color = Color.White
        )
    }
}

@Composable
fun VisBottomNavigation(
    onButtonClick: (String) -> Unit,
){
    BottomNavigationBar(
        buttonNames = listOf("Event history", "Events", "Profile"),
        imageVectors = listOf(R.drawable.event_history_icon, R.drawable.progile_icon),
        onButtonClick = onButtonClick
    )
}
@Composable
fun OrgPerBottomNavigation(
    onButtonClick: (String) -> Unit,
){
    BottomNavigationBar(
        buttonNames = listOf("Profile", "Events", "Chat"),
        imageVectors = listOf(R.drawable.progile_icon, R.drawable.chat_icon),
        onButtonClick = onButtonClick
    )
}

@Composable
fun BottomNavigationBar(
    buttonNames:List<String>,
    imageVectors: List<Int>,
    onButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val boxHeight = 90.dp
    val circleRadius = boxHeight * 0.9f / 2

    Box(
        modifier = modifier
            .background(Color.Transparent)
            .fillMaxWidth()
            .height(boxHeight)
    ) {
        Row(
            modifier = modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth()
                .align(Alignment.BottomEnd),
        ) {
            Button(
                shape = RoundedCornerShape(20.dp, 0.dp, 0.dp, 0.dp),
                modifier = modifier
                    .weight(1f)
                    .fillMaxHeight(),
                onClick = {
                    onButtonClick(buttonNames[0])
                },
                colors = ButtonDefaults.buttonColors(containerColor = Purple)

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier
                        .padding(end = 20.dp)
                ) {
                    Icon(
                        modifier = modifier
                            .width(24.dp)
                            .height(24.dp),
                        painter = painterResource(id = imageVectors[0]),
                        contentDescription = null,
                        tint = Color.White
                    )
                    Text(
                        fontSize = 13.sp,
                        text = buttonNames[0]
                    )
                }
            }

            Button(
                shape = RoundedCornerShape(0.dp, 20.dp, 0.dp, 0.dp),
                modifier = modifier
                    .weight(1f)
                    .fillMaxHeight(),
                onClick = {
                    onButtonClick(buttonNames[2])
                },
                colors = ButtonDefaults.buttonColors(containerColor = Purple)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier
                        .padding(start = 30.dp)
                ) {
                    Icon(
                        modifier = modifier
                            .width(24.dp)
                            .height(24.dp),
                        painter = painterResource(id = imageVectors[1]),
                        contentDescription = null,
                        tint = Color.White
                    )
                    Text(
                        fontSize = 13.sp,
                        text = buttonNames[2]
                    )
                }
            }
        }

        Button(
            shape = CircleShape,
            border = BorderStroke(1.dp, Color.White),
            modifier = modifier
                .size(circleRadius * 2)
                .align(Alignment.TopCenter),
            onClick = {
                onButtonClick(buttonNames[1])
            },
            colors = ButtonDefaults.buttonColors(containerColor = Purple)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = modifier
                        .width(24.dp)
                        .height(24.dp),
                    painter = painterResource(id = R.drawable.microphone_icon),
                    contentDescription = null,
                    tint = Color.White
                )
                Text(
                    fontSize = 10.sp,
                    text = buttonNames[1]
                )
            }
        }
    }
}
