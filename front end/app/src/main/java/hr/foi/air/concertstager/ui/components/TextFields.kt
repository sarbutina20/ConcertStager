package hr.foi.air.concertstager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hr.foi.air.concertstager.R
import hr.foi.air.concertstager.ui.theme.BlueLight
import hr.foi.air.concertstager.ui.theme.OrangeLight
import hr.foi.air.concertstager.ui.theme.Purple
import hr.foi.air.concertstager.ws.models.response.Venue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledComboBox(
    label: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(selectedItem) }


    Column(
        modifier = modifier
            .padding(start = 20.dp, end = 20.dp)
    ) {
        OutlinedTextField(
            value = selectedValue,
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                }
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            colors = TextFieldDefaults.textFieldColors(containerColor = BlueLight)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 60.dp, end = 60.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.background),
        ) {
            items.forEach { item ->
                DropdownMenuItem(text = { Text(text = item) }, onClick = {
                    selectedValue = item
                    onItemSelected(item)
                    expanded = false
                })
                }
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    label: String = "Password",
    value: String,
    onValueChange: (newValue: String) -> Unit,

    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
) {
    StyledOneLineTextField(
        imageVector = Icons.Default.Lock,
        label = label,
        value = value,
        onValueChange = onValueChange,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VenueStyledComboBox(
    label: String,
    items: List<Venue>,
    selectedItem: Venue,
    onItemSelected: (Venue) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }


    Column(
        modifier = modifier
            .padding(bottom = 8.dp)
    ) {
        OutlinedTextField(
            value = selectedItem.name!!,
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                }
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            colors = TextFieldDefaults.textFieldColors(containerColor = BlueLight)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 60.dp, end = 60.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.background),
        ) {
            items.forEach { item ->
                DropdownMenuItem(text = { Text(text = item.name!!)  }, onClick = {
                    onItemSelected(item)
                    expanded = false
                })
            }
        }
    }
}

@Composable
fun SmallTextLabel(
    text: String,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .background(
                color = BlueLight,
                shape = RoundedCornerShape(10.dp)
            )
    ){
        Text(
            text = text,
            modifier = modifier
                .padding(5.dp)
        )
    }

}

@Composable
fun ClickableTextLabel(
    text: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .clickable { onClick() }
            .background(
                color = OrangeLight,
                shape = RoundedCornerShape(10.dp)
            )
    ){
        Text(
            text = text,
            modifier = modifier
                .padding(5.dp)
        )
    }
}

@Composable
fun LabelWithIcon(
    content: String,
    imageVector: ImageVector,
    modifier: Modifier=Modifier
){
    Row(
        modifier = modifier
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = Color.White)
        Spacer(modifier = modifier.width(8.dp))
        Text(
            text = content,
            color = Color.White,
            fontSize = 10.sp,
            )
    }
}

@Composable
fun PasswordLabelWithIcon(
    content: String,
    imageVector: ImageVector,
    modifier: Modifier=Modifier
){
    Row(
        modifier = modifier
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = Color.White)
        Spacer(modifier = modifier.width(8.dp))
        Text(
            buildAnnotatedString {
                repeat(content.length){
                    append("*")
                }
            },
            color = Color.White,
            fontSize = 10.sp,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledOneLineTextField(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    onValueChange: (newValue: String) -> Unit,
    imageVector: ImageVector = Icons.Outlined.AccountCircle,
    readOnly : Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 40.dp)
    ) {
            OutlinedTextField(
                leadingIcon = { Icon(imageVector, contentDescription = null)},
                label = {
                    Text(text = label,
                        color = Color.Black)
                },
                value = value,
                onValueChange = onValueChange,
                visualTransformation = visualTransformation,
                singleLine = singleLine,
                keyboardOptions = keyboardOptions,
                modifier = modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(containerColor = BlueLight,
                    textColor = Color.Black ),
                enabled = !readOnly
            )
    }
}

@Composable
fun UnderlinedText(
    onButtonClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
            .height(35.dp)
    ){
        Row(

        ){
            Text(
                text = label,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
        Divider(
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MessageInput(
    onSendMessageClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var messageText by remember { mutableStateOf(TextFieldValue()) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(27.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = messageText,
            onValueChange = {
                messageText = it
            },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            placeholder = {
                Text(
                    text = "Type",
                    style = LocalTextStyle.current.copy(color = Color.Black.copy(alpha = 0.6f))
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                                if (messageText.text.isNotBlank()) {
                                    onSendMessageClick(messageText.text)
                                    messageText = TextFieldValue()
                                }
                        }
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    if (messageText.text.isNotBlank()) {
                        onSendMessageClick(messageText.text)
                        messageText = TextFieldValue()
                    }
                    keyboardController?.hide()
                }
            ),
            shape = RoundedCornerShape(27.dp),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                containerColor = BlueLight
            )
        )
    }
}

@Composable
fun SenderMessage(
    userName: String,
    message: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Default icon for sender
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            tint = Purple,
            modifier = modifier.size(40.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(
                text = userName,
                fontWeight = FontWeight.Bold,
                color = Purple
            )
            Box(
                modifier = Modifier
                    .background(Purple, RoundedCornerShape(8.dp))
                    .padding(8.dp),
            ) {
                Text(
                    text = message,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun MyMessage(
    message: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(BlueLight, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Text(
                text = message
            )
        }
    }
}





@Preview(showBackground = true)
@Composable
fun ShowTextFields() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            ,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
    var textValue by remember { mutableStateOf("") }

    StyledOneLineTextField(
        label = "Some label",
        value = textValue,
        onValueChange = {
            textValue = it
        },
        // Optional parameters
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = VisualTransformation.None
    )

        Icon(
            imageVector = Icons.Default.Email, contentDescription = "Email",
            tint = Color.White)
        LabelWithIcon(content = "ilitre100@gmail.com", imageVector = Icons.Default.Email)
        //PasswordTextField(value = textValue, onValueChange = {textValue.it})


    // Other Composables or UI elements can be added here
        val selectedRole by remember { mutableStateOf("Select Role") }

        StyledComboBox(
            label = "Role",
            items = listOf("Admin", "User", "Guest"),
            selectedItem = selectedRole,
            onItemSelected = {},
            modifier = Modifier.padding(16.dp)
        )
        MessageInput(onSendMessageClick = {})

        SenderMessage(
            userName = "Valentina Juhas",
            message = "" )
        MyMessage(message = "OK OKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK")

}

}