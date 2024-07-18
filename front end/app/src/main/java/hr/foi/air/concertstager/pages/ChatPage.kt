package hr.foi.air.concertstager.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.chat.models.Message
import hr.foi.air.concertstager.ui.components.GroupMainCard
import hr.foi.air.concertstager.ui.components.MessageInput
import hr.foi.air.concertstager.ui.components.MyMessage
import hr.foi.air.concertstager.ui.components.OrgPerBottomNavigation
import hr.foi.air.concertstager.ui.components.PreviousPageButton
import hr.foi.air.concertstager.ui.components.SenderMessage
import hr.foi.air.concertstager.ui.components.StyledOneLineTextField
import hr.foi.air.concertstager.viewmodels.ChatViewModel

data class Message(
    val text: String,
    val author: String,
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ChatPage(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = viewModel(),
    receiverName: String,
    returnToListOfUsers: () -> Unit
) {
    var messageToSend by remember { mutableStateOf("") }
    val loggedUser = UserLoginContext.loggedUser
    var idToSendTo by remember { mutableStateOf(loggedUser?.userId!!.toString()) }
    var messagesFromStorage by remember { mutableStateOf(emptyList<Message>()) }
    var lastMessageSentTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

    if(idToSendTo.isNotEmpty())
    {
        messagesFromStorage = viewModel.getAllMessages(loggedUser?.userId!!, idToSendTo.toInt())
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            OrgPerBottomNavigation(
                onButtonClick = {})
        }
    ) { innerPadding ->
        MaterialTheme {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(

                    ) {
                        PreviousPageButton(onClick = { returnToListOfUsers() })
                        StyledOneLineTextField(
                            label = "ID to send to: ",
                            value = idToSendTo,
                            onValueChange ={
                                idToSendTo=it
                            } )
                    }
                }
                GroupMainCard(
                    groupName = receiverName)

                ConstraintLayout(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val (messages, messageInput) = createRefs()

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(messages) {
                                top.linkTo(parent.top)
                                bottom.linkTo(messageInput.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                height = Dimension.fillToConstraints
                            },
                        reverseLayout = true
                    ) {
                        if (messagesFromStorage.isEmpty()) {
                            Log.i("ChatPage", "Message content: THERE ARE NO MESSAGES")
                            item {
                                Text(text = "You haven't exchanged any messages with this user! Your ID: ${loggedUser?.userId}")
                            }
                        } else {
                            for (message in messagesFromStorage) {
                                Log.i("ChatPage", "Message content, sender, receiver: ${message.content} ${message.senderId} ${message.receiverId}\n")
                            }
                            itemsIndexed(messagesFromStorage) { _, existingMessage ->
                                if (existingMessage.senderId == loggedUser?.userId) {
                                    MyMessage(message = existingMessage.content!!)
                                } else {
                                    SenderMessage(userName = receiverName, message = existingMessage.content!!)
                                }
                            }
                        }
                    }

                    MessageInput(
                        onSendMessageClick = {
                            val currentTime = System.currentTimeMillis()
                            if (currentTime - lastMessageSentTime > 1000)
                            {
                                messageToSend = it
                                viewModel.sendMessage(idToSendTo, messageToSend)
                                lastMessageSentTime = currentTime
                                Log.i("ChatPage", "Message to send: $messageToSend")
                            }
                        },
                        modifier = Modifier
                            .constrainAs(messageInput) {
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ShowChatPage(
    modifier: Modifier = Modifier,
){
    ChatPage(
        receiverName = "Ivan Horvat",
        returnToListOfUsers = {}
    )
}
