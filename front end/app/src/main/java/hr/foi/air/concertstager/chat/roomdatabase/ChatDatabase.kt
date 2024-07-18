package hr.foi.air.concertstager.chat.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hr.foi.air.concertstager.chat.models.Message
import hr.foi.air.concertstager.chat.models.dao.MessageDAO

@Database(entities = [Message::class], version = 1, exportSchema = false)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun getMessageDao(): MessageDAO
    companion object {
        @Volatile
        private var implementedInstance: ChatDatabase? = null
        fun getInstance(): ChatDatabase {
            if (implementedInstance == null) {
                throw NullPointerException("Database instance has not yet been created!")
            }
            return implementedInstance!!
        }
        fun buildInstance(context: Context) {
            if (implementedInstance == null) {
                val instanceBuilder = Room.databaseBuilder(
                    context,
                    ChatDatabase::class.
                    java,
                    "chat.db"
                )
                instanceBuilder.fallbackToDestructiveMigration()
                instanceBuilder.allowMainThreadQueries()
                instanceBuilder.build()
                implementedInstance = instanceBuilder.build()
            }
        }
    }
}
