package hr.foi.air.concertstager.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hr.foi.air.concertstager.ui.components.ConcertForm1
import hr.foi.air.concertstager.ui.components.PreviousPageButton
import hr.foi.air.concertstager.ui.theme.ConcertStagerTheme
import hr.foi.air.concertstager.ui.theme.Purple

@Composable
fun NewEventPage(
    onBackClicked: () -> Unit,
    onSaveClicked: () -> Unit,
) {
    ConcertStagerTheme {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 20.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                PreviousPageButton(
                    onClick = onBackClicked
                )
            }

            Text(
                text = "Create new event",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Purple,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 40.dp, bottom = 7.dp)
            )
                    ConcertForm1(
                        onSaveClicked =  onSaveClicked
                    )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowNewEventPage(
    modifier: Modifier= Modifier
){

}