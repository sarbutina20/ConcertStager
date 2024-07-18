package hr.foi.air.concertstager.ws.models.response

import com.google.gson.annotations.SerializedName
data class GoogleSignInTokenResponse(
    @SerializedName("googleIdTokenValidity") var googleIdTokenValidity: String
)
