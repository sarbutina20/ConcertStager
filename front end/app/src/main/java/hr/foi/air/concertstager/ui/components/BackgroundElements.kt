package hr.foi.air.concertstager.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hr.foi.air.concertstager.R
import hr.foi.air.concertstager.ui.theme.BlueLight
import hr.foi.air.concertstager.ui.theme.OrangeLight
import hr.foi.air.concertstager.ui.theme.Purple

@Composable
fun RoundedBackgroundBox(
    fontSize: Int,
    contentText: String,
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(
                color = Purple,
                shape = RoundedCornerShape(0.dp, 0.dp, 40.dp, 40.dp)
            )
            .padding(32.dp)
    ){
        Text(text = contentText,
            color = Color.White,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.logo_concert_stager),
            contentDescription = null,
            modifier = modifier
                .size(100.dp)  // Set the size of the icon as needed
                .align(Alignment.BottomCenter)
        )

    }
}


@Composable
fun RoundedUserBox(
    imageResource: Int = R.drawable.profile_picture_opca_iopasnost,
    onBack: () -> Unit,
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy((-70).dp),
    ) {
        Box(modifier = modifier,
            contentAlignment = Alignment.TopStart
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .background(
                        color = Purple,
                        shape = RoundedCornerShape(0.dp, 0.dp, 40.dp, 40.dp)
                    )
            ) {


            }
            IconButton(onClick = onBack, modifier = Modifier.padding(16.dp)) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
        }
        ProfilePicture(imageResource = imageResource)
    }
}

@Composable
fun ProfilePicture(
    imageResource: Int,
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
){Box(
    modifier = modifier
        .padding(start = 20.dp)
)
{
    Image(
        painter = painterResource(imageResource),
        contentDescription = null,
        modifier = modifier
            .size(120.dp)
            .clip(CircleShape)
    )
}

}


@Composable
fun CardWithIcons(
    iconActions: List<() -> Unit> = emptyList(),
) {
    Box(
        modifier = Modifier
            .width(344.dp)
            .height(265.dp)
            .border(1.dp, Color.Black, shape = RoundedCornerShape(40.dp))
            .background(
                color = BlueLight,
                shape = RoundedCornerShape(40.dp),
            )
            .padding(32.dp)
    ) {
        Text(
            text = "Choose your role:",
            fontSize = 16.sp
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(top = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))
            Row() {
                CircleIconWithText("Visitor", R.drawable.ic_launcher_foreground, iconActions.getOrNull(0))
                Spacer(modifier = Modifier.width(20.dp))
                CircleIconWithText("Organizer", R.drawable.ic_launcher_foreground, iconActions.getOrNull(1))
                Spacer(modifier = Modifier.width(20.dp))
                CircleIconWithText("Performer", R.drawable.ic_launcher_foreground, iconActions.getOrNull(2))
            }
        }
    }
}

@Composable
fun CircleIconWithText(
    text: String,
    iconRes: Int,
    onClick: (() -> Unit)?,
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .size(65.dp)
                .background(OrangeLight, shape = CircleShape)
                .padding(8.dp)
                .clickable { onClick?.invoke() }, // Make the Box clickable
            contentAlignment = Alignment.Center
        ) {
            // Replace with your actual icon resource
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = modifier.size(50.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = modifier.height(4.dp))

        Text(
            text = text,
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Show(){
    Column(
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        //RoundedBackgroundBox(48, "CONCERT STAGER")
        //RoundedBackgroundBox(48, "Welcome to Concert Stager!!")
        //CardWithIcons()
        RoundedUserBox(onBack = {})
    }
}