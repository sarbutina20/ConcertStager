package hr.foi.air.concertstager.chat

import android.util.Log
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.LoginUserData
import hr.foi.air.concertstager.ws.network.NetworkService
import javax.net.ssl.X509TrustManager

class ChatHubConnection {
    val serverUrl = "https://192.168.5.83:7096/chathub"
    //UserLoginContext.loggedUser?.user_id!!
    companion object {
        val userId: Int = (1..10).random()
    }

    val fullUrl = "$serverUrl?userId=$userId"

    val names = listOf(
        "John Doe",
        "Jane Doe",
        "Michael Smith",
        "Emily Johnson",
        "Christopher Davis",
        "Jessica Brown",
        "David Wilson",
        "Amanda Taylor",
        "Daniel Anderson",
        "Olivia Martinez"
    )


    val hubConnection: HubConnection = HubConnectionBuilder
        .create(fullUrl)
        .setHttpClientBuilderCallback { builder ->
            builder.sslSocketFactory(NetworkService.sslContext.socketFactory, NetworkService.trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true }
        }
        .build()


    fun startConnection() {
        Log.i("ClientID,userID:", userId.toString())
        val name : String = names.random()
        val dummyUser = LoginUserData(
            userId,
            "google Id",
        name,
        "$name@example.com",
        1,
        2,
            "+123456789",
        "someJwtToken",
        )
        UserLoginContext.loggedUser = dummyUser
        Log.i("UserContextLogin:", UserLoginContext.loggedUser.toString())
        if (hubConnection.connectionState == HubConnectionState.DISCONNECTED) {
            hubConnection.start().blockingAwait()
        }

    }

    fun stopConnection() {
        if (hubConnection.connectionState == HubConnectionState.CONNECTED) {
            hubConnection.stop().blockingAwait()
        }
    }

    fun sendMessage(senderId: String, receiverId: String, message: String) {
        hubConnection.send("SendMessage", senderId, receiverId, message)
    }

    fun setOnMessageReceivedListener(listener: (String, String, String) -> Unit){
        hubConnection.on("ReceiveMessage", { receiverId, senderId, message ->
            listener(receiverId, senderId, message) }, String::class.java, String::class.java, String::class.java)
    }
}