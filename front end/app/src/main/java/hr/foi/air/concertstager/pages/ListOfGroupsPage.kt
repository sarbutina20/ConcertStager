package hr.foi.air.concertstager.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import hr.foi.air.concertstager.ui.components.GroupChatCard
import hr.foi.air.concertstager.ui.components.OrgPerBottomNavigation
import hr.foi.air.concertstager.ui.theme.Purple

data class GroupCardData(
    val name: String
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfGroupsPage(
    modifier: Modifier = Modifier,
    openChatPageWithUser : (String) -> Unit
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            OrgPerBottomNavigation(
                onButtonClick = {})
        }
    ) { innerPadding ->
        MaterialTheme {
            val groups = listOf(
                GroupCardData("Ivan Horvat"),
                GroupCardData("Marija Knežević"),
                GroupCardData("Tin Kovačević")
            )

            Column (
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Chat",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Purple,
                    modifier = modifier
                        .align(Alignment.Start)
                        .padding(top = 20.dp, start = 20.dp)
                )
                /* Button(
                    onClick = {  },
                    modifier = Modifier
                        .height(35.dp)
                        .padding(end = 10.dp)
                        .align(Alignment.End),
                    enabled = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        OrangeLight
                    )
                ) {
                    Text(
                        "New chat",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.new_chat_icon),
                        contentDescription = null,
                        tint = Color.Black
                    )
                } */

                LazyColumn {
                    items(groups) { group ->
                        GroupChatCard(
                            groupName = group.name,
                            onClick = {
                                openChatPageWithUser(group.name)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowListOfGroupsPage(
    modifier: Modifier = Modifier
){
    ListOfGroupsPage(
        openChatPageWithUser = {}
    )
}