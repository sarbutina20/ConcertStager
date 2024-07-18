package hr.foi.air.concertstager.ws.models.response

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("grade") var grade: Int? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("userReviewId") var userReviewId: Int? = null,
    @SerializedName("userId") var userId: Int? = null,
    @SerializedName("venueId") var venueId: Int? = null
)
