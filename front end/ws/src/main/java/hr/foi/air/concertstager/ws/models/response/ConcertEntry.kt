package hr.foi.air.concertstager.ws.models.response

import com.google.gson.annotations.SerializedName

data class ConcertEntry(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("date") var date: String? = null,
    @SerializedName("isAccepted") var isAccepted: Boolean? = null,
    @SerializedName("userId") var userId: Int? = null,
    @SerializedName("concertId") var concertId: Int? = null,
)
