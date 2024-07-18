package hr.foi.air.concertstager.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hr.foi.air.concertstager.R
import hr.foi.air.concertstager.ui.theme.BlueLight
import hr.foi.air.concertstager.ui.theme.Purple


@Composable
fun BigButton(
    label: String,
    onClick: () ->Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(47.dp)
            .padding(start = 40.dp, end = 40.dp)
            .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
            ,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE9CE), contentColor = Color.Black)
        ) {
        Text(label,
            fontSize = 24.sp,
            fontFamily = FontFamily.Monospace

            )
    }
}

@Composable
fun TitleButton(
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier,
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .height(70.dp)
            .width(180.dp)
    ) {
        Text(
            text = name,
            modifier = modifier
                .padding(bottom = if (isSelected) 4.dp else 0.dp)
                .align(Alignment.Center)
                .padding(4.dp),
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = modifier
                .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Black)
                .height(if (isSelected) 3.dp else 1.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)

        )
    }
}


@Composable
fun ButtonWithIcon(
    onClick: () ->Unit,
    imageResource: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    Box(
        modifier = modifier
            .padding(start = 20.dp)
            .clip(CircleShape)
    )
    {
        Image(
            painter = painterResource(imageResource),
            contentDescription = null,
            modifier = modifier
                .size(40.dp)
                .clickable(onClick = onClick)
                .clip(CircleShape)
        )
    }
}

@Composable
fun NextPageButton(
    onClick: () ->Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true){
    Button(
        onClick = onClick,
        modifier
            .height(48.dp)
            //.width(48.dp)
            .border(1.dp, Color.Black, shape = CircleShape),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = BlueLight, contentColor = Color.Black)
    ){
        Icon(Icons.Outlined.KeyboardArrowRight,
            contentDescription = null,
            modifier
                .padding(end = 0.dp))

    }
}

@Composable
fun PreviousPageButton(
    onClick: () ->Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true){
    Button(
        onClick = onClick,
        modifier
            .height(48.dp)
            //.width(48.dp)
            .border(1.dp, Color.Black, shape = CircleShape),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = BlueLight, contentColor = Color.Black)
    ){
        Icon(Icons.Outlined.KeyboardArrowLeft,
            contentDescription = null,
            modifier
                .padding(end = 0.dp))

    }
}

@Composable
fun DetailsButton(
    label: String,
    onClick: () ->Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true){
    Button(
        onClick = onClick,
        modifier
            .height(38.dp),
            //.width(48.dp)
            //.border(1.dp, Color.Black),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Purple, contentColor = Color.White)
    ){
        Text(label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

    }
}

@Composable
fun SmallButtonWithIcon(
    label: String,
    imageVector: ImageVector,
    onClick: () ->Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    Button(
        onClick = onClick,
        modifier = modifier
            .height(35.dp)
            //.border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
            .background(
                color = Purple,
                shape = RoundedCornerShape(8.dp)
            )
        ,
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFE9CE), contentColor = Color.Black)
    ) {
        Text(label,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
        Spacer(modifier = modifier.width(5.dp))
        Icon(
            imageVector = imageVector,
            contentDescription = null)
    }
}

@Preview(showBackground = true)
@Composable
fun ShowButtons() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BigButton(label = "Log in", onClick = { /*TODO*/ })
        BigButton(label = "Sign up", onClick = { /*TODO*/ })
        NextPageButton(onClick = { /*TODO*/ })
        PreviousPageButton(onClick = { /*TODO*/ })
        DetailsButton("See details", onClick = { /*TODO*/ })
        ButtonWithIcon(onClick = { /*TODO*/ }, imageResource = R.drawable.google_icon)
        ButtonWithIcon(onClick = { /*TODO*/ }, imageResource = R.drawable.logo_concert_stager)
        SmallButtonWithIcon(label = "Edit", imageVector = Icons.Default.Create,
            onClick = { /*TODO*/ })
    }
}
