package hr.foi.air.concertstager.chat.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import hr.foi.air.concertstager.chat.helpers.DateConverter
import java.util.Date

@Entity(tableName = "messages")
@TypeConverters(DateConverter::class)
data class Message(
    @PrimaryKey(autoGenerate = true)
    val messageId: Int = 0,

    val senderId: Int,
    val senderName: String?,
    val receiverId: Int,
    val receiverName: String?,
    val content: String?,
    val date: Date = Date()
) {
    constructor(
        senderId: Int,
        senderName: String?,
        receiverId: Int,
        receiverName: String?,
        content: String?,
        date: Date = Date()
    ) : this(
        0,
        senderId = senderId,
        senderName = senderName,
        receiverId = receiverId,
        receiverName = receiverName,
        content = content,
        date = date
    )
}


