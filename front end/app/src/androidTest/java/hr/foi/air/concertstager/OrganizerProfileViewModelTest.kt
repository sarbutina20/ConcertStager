package hr.foi.air.concertstager

import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OrganizerProfileViewModelTest {
    private lateinit var mockWebServer: MockWebServer

    @Test
    fun updateOrganizer_OrganizerNotFound_ReturnErrorResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "Organizer not exists",
                  "success": false,
                  "message": "Organizer not exists"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Organizer/14")

        val userUpdateBody = """
                {
                  "name": "Karla",
                  "email": "karla@gmail.com",
                  "password": "karla123",
                  "contactNumber": "0912819252",
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = userUpdateBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).patch(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Organizer not exists"))
        }
    }

    @Test
    fun updateOrganizer_ValidOrganizerProperties_ReturnSuccessfulResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 5,
                      "contactNumber": "0971825125",
                      "googleId": "qwertz",
                      "name": "Lidija",
                      "email": "lidija@gmail.com",
                      "password": "lidija123",
                      "roleId": 1
                    }
                  ],
                  "success": true,
                  "message": "Organizer updated successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Organizer/7")

        val userUpdateBody = """
                {
                  "name": "Lidija",
                  "email": "lidija@gmail.com",
                  "password": "lidija123",
                  "contactNumber": "0971825125",
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = userUpdateBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).patch(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Organizer updated successfully"))
        }
    }
}