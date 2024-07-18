package hr.foi.air.concertstager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.foi.air.concertstager.ui.theme.Purple
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearchTextChanged: (String) -> Unit
){
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()


    TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                // Handle text changes here
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(40.dp),
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            placeholder = {
                Text(
                    text = "Search",
                    style = LocalTextStyle.current.copy(color = Color.White.copy(alpha = 0.6f))
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    // Handle search action here if needed
                    keyboardController?.hide()
                }
            ),
            shape = RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                containerColor = Purple
            )
        )
    LaunchedEffect(searchText) {
        coroutineScope.launch {
            delay(500)
            onSearchTextChanged(searchText.text)
        }
    }
}

@Composable
fun MyCustomBottomNavigationBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Purple)
            .clip(shape = RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // First item
        IconButton(
            onClick = {
                // Handle click for the first item
            }
        ) {
            Icon(
                imageVector = Icons.Default.Add, // Replace with your icon
                contentDescription = "First Icon"
            )
        }

        // Second item with a circle icon
        Box(
            modifier = Modifier
                .size(56.dp) // Set the size of the circle
                .clip(CircleShape)
                .background(Color.Blue), // Set the color of the circle
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = {
                    // Handle click for the circle item
                }
            ) {
                Icon(
                    imageVector = Icons.Default.AccountBox, // Replace with your icon
                    contentDescription = "Circle Icon",
                    tint = Color.White
                )
            }
        }

        // Third item
        IconButton(
            onClick = {
                // Handle click for the third item
            }
        ) {
            Icon(
                imageVector = Icons.Default.Search, // Replace with your icon
                contentDescription = "Third Icon"
            )
        }
    }
}

@Preview (showBackground = true)
@Composable
fun ShowNavigationElements(
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        SearchBar(
            onSearchTextChanged = {
                // Handle search click here
            }

        )
        MyCustomBottomNavigationBar()
    }

}

