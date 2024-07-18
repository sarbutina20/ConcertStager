package hr.foi.air.concertstager.chat.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import hr.foi.air.concertstager.chat.models.Message

@Dao
interface MessageDAO {
    @Query("SELECT * FROM messages WHERE (senderId = :senderId AND receiverId = :receiverId) OR (senderId = :receiverId AND receiverId = :senderId) ORDER BY Date DESC LIMIT 20")
    fun getAllChatGroupMessages(senderId : Int, receiverId : Int): List<Message>

    @Query("SELECT * FROM messages")
    fun getAllMessagesFromDevice() : List<Message>
    @Insert
    fun insertMessage(message: Message)
}