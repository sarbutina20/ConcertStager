package hr.foi.air.concertstager.ws.models.response

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("googleId") var googleId: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("roleId") var roleId: Int? = null
)
