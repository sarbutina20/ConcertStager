package hr.foi.air.concertstager.ws.models.response

import com.google.gson.annotations.SerializedName

data class Concert (
    @SerializedName("id") var id: Int? = null,
    @SerializedName("venueId") var venueId: Int? = null,
    @SerializedName("userId") var userId: Int? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("startDate") var startDate: String? = null,
    @SerializedName("endDate") var endDate: String? = null
)