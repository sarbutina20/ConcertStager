package hr.foi.air.concertstager.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import hr.foi.air.concertstager.chat.ChatHubConnection
import hr.foi.air.concertstager.chat.models.Message
import hr.foi.air.concertstager.chat.roomdatabase.ChatDatabase
import hr.foi.air.concertstager.context.UserLoginContext

class ChatViewModel : ViewModel() {
        private val chatHubConnection = ChatHubConnection()
        val messageDao = ChatDatabase.getInstance().getMessageDao()
        fun setMessageReceived(){
            chatHubConnection.setOnMessageReceivedListener {receiverId, senderId, messageContent ->
                Log.i("ChatViewModel", "receiverId: $receiverId")
                Log.i("ChatViewModel", "senderId: $senderId")
                Log.i("ChatViewModel", "message: $messageContent")
                val message = Message(
                    senderId.toInt(),
                    "Anonymous Sender Name",
                    receiverId.toInt(),
                    UserLoginContext.loggedUser?.user_name!!,
                    messageContent,
                )
                messageDao.insertMessage(message)
                Log.i("ChatViewModel", "Message object received: $message")
            }
        }
        init {
            setMessageReceived()
            Log.i("ChatViewModel", "Listener initialized!")
            chatHubConnection.startConnection()
        }

        fun sendMessage(receiverId: String, messageContent: String) {
            Log.i("ChatViewModel", "Prije kreiranja objekta poruke")
            val message = Message(
                UserLoginContext.loggedUser?.userId!!,
                UserLoginContext.loggedUser?.user_name!!,
                receiverId.toInt(),
                "Anonymous Receiver Name",
                messageContent
                )
            Log.i("ChatViewModel", "Kreiran objekt poruke: $message")
            chatHubConnection.sendMessage(UserLoginContext.loggedUser?.userId!!.toString(), receiverId, messageContent)

            messageDao.insertMessage(message)
            Log.i("ChatViewModel", "Private message sent!")
        }

        fun getAllMessages(senderId : Int, receiverId : Int) : List<Message>{
            return messageDao.getAllChatGroupMessages(senderId, receiverId)
        }
        override fun onCleared() {
            super.onCleared()
            chatHubConnection.stopConnection()
        }
}