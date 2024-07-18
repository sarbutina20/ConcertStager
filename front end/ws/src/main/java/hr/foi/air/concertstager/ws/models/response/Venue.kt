package hr.foi.air.concertstager.ws.models.response

import com.google.gson.annotations.SerializedName

data class Venue (
    @SerializedName("id") var id: Int? = null,
    @SerializedName("userId") var userId: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("capacity") var capacity: Int? = null
)