package hr.foi.air.concertstager.core.login

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Locale

class DateFormatter {
    companion object{
        @SuppressLint("SimpleDateFormat")
        fun displayDate(dateToFormat: String) : String{
            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val displayDateFormat = SimpleDateFormat("yyyy.MM.dd. HH:mm")
            val date = inputDateFormat.parse(dateToFormat)
            return displayDateFormat.format(date!!)
        }

        @SuppressLint("SimpleDateFormat")
        fun getDate(dateToFormat: String) : String{
            val inputDateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
            val displayDateFormat = SimpleDateFormat("yyyy.MM.dd. HH:mm")
            val date = inputDateFormat.parse(dateToFormat)
            return displayDateFormat.format(date!!)
        }

        @SuppressLint("SimpleDateFormat")
        fun saveDate(dateToFormat: String) : String{
            val inputDateFormat = SimpleDateFormat("yyyy.MM.dd. HH:mm", Locale.getDefault())
            val saveDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val date = inputDateFormat.parse(dateToFormat)
            return saveDateFormat.format(date!!)
        }
    }
}