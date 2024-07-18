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
class CreateConcertViewModelTest {
private lateinit var mockWebServer : MockWebServer
    @Test
    fun getVenues_VenuesDoesNotExists_SuccessResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [],
                  "success": true,
                  "message": "Successfully retrieved reviews for this user."
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Venue")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Successfully retrieved reviews for this user."))
        }
    }

    @Test
    fun getVenues_VenuesExists_SuccessResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 1,
                      "userId": 1,
                      "name": "Dvorana u Parizu",
                      "decription": "Ogromna dvorana",
                      "city": "Pariz",
                      "address": "Pariška ulica",
                      "capacity": 25000
                    },
                    {
                      "id": 3,
                      "userId": 5,
                      "name": "Nogometno igraliste",
                      "decription": "Igraliste u Zagrebu",
                      "city": "Zagreb",
                      "address": "Kranjceviceva 19",
                      "capacity": 25000
                    },
                    {
                      "id": 4,
                      "userId": 13,
                      "name": "Poljud",
                      "decription": "Nogometni stadion",
                      "city": "Split",
                      "address": "Hajducka 20",
                      "capacity": 35000
                    },
                    {
                      "id": 5,
                      "userId": 15,
                      "name": "Nogometni stadion Varaždin",
                      "decription": "Stadion kluba NK Varazdin",
                      "city": "Varazdin",
                      "address": "Zagrebacka 23",
                      "capacity": 10800
                    },
                    {
                      "id": 6,
                      "userId": 19,
                      "name": "Aldo Drosina",
                      "decription": "Stadion NK Istre 1961",
                      "city": "Pula",
                      "address": "Istrijansk 23",
                      "capacity": 10000
                    }
                  ],
                  "success": true,
                  "message": "Venues found successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Venue")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Venues found successfully"))
        }
    }

    @Test
    fun createConcert_AllPropertiesAreSet_ReturnSuccessfulResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 15,
                      "venueId": 1,
                      "userId": 5,
                      "description": "Koncert",
                      "name": "Testni koncert",
                      "startDate": "2024-01-13T14:51:57.043Z",
                      "endDate": "2024-01-13T14:51:57.043Z"
                    }
                  ],
                  "success": true,
                  "message": "Concert created successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert")

        val createConcertBody = """
                {
                  "venueId": 1,
                  "userId": 5,
                  "description": "Koncert",
                  "name": "Testni koncert",
                  "startDate": "2024-01-13T14:51:57.043Z",
                  "endDate": "2024-01-13T14:51:57.043Z"
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = createConcertBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).post(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Concert created successfully"))
        }
    }
}