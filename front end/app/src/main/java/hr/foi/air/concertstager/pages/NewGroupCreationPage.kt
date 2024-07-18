package hr.foi.air.concertstager.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hr.foi.air.concertstager.ui.components.OrgPerBottomNavigation
import hr.foi.air.concertstager.ui.components.PreviousPageButton
import hr.foi.air.concertstager.ui.theme.Purple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewGroupCreationPage(
    modifier: Modifier = Modifier
){
    Scaffold(
        modifier = modifier,
        bottomBar = {
            OrgPerBottomNavigation(
                onButtonClick = {})
        }
    ) { innerPadding ->
        run {
            MaterialTheme {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column() {
                            PreviousPageButton(onClick = { /*TODO*/ })
                        }
                    }
                    Text(
                        text = "New chat",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Purple,
                        modifier = modifier
                            .align(Alignment.Start)
                            .padding(top = 5.dp, start = 20.dp)
                    )

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowNewGroupCreationPage(
    modifier: Modifier = Modifier
){
    NewGroupCreationPage()
}
