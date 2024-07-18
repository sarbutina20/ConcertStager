package hr.foi.air.concertstager.ws.models.response

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
)
