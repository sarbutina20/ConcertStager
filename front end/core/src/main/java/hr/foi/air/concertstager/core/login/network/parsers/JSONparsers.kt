package hr.foi.air.concertstager.core.login.network.parsers

import hr.foi.air.concertstager.core.login.LoginUserData
import org.json.JSONObject

fun parseGoogleIdTokenValidity(responseBody: String): Boolean {
        val json = JSONObject(responseBody)
        val googleIdTokenValidity = json.optString("googleIdTokenValidity", "False")
        return googleIdTokenValidity.equals("True", ignoreCase = true)
}

fun parseUserFromUserLoginJSON(responseBody: String) : LoginUserData? {
    val json = JSONObject(responseBody)
    val dataArray = json.optJSONArray("data")
    val userObject = dataArray?.optJSONObject(0)
    return if (dataArray != null && userObject != null) {
        LoginUserData(
            userObject.getInt("id"),
            userObject.getString("googleId"),
            userObject.getString("name"),
            userObject.getString("email"),
            userObject.getInt("roleId"),
            userObject.getInt("genreId"),
            userObject.getString("contactNumber"),
            userObject.getString("jwt"),
        )
    } else {
        null
    }
}