package hr.foi.air.concertstager

import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OrganizerHomepageViewModelTest {
    private lateinit var mockWebServer: MockWebServer

    @Test
    fun getOrganizerConcerts_OrganizerHasNoConcerts_ErrorResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "Organizer doesn't have any concerts",
                  "success": false,
                  "message": "Organizer doesn't have any concerts"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/organizer/21")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Organizer doesn't have any concerts"))
        }
    }

    @Test
    fun getOrganizerConcerts_OrganizerHasConcerts_SuccessResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 4,
                      "venueId": 4,
                      "userId": 13,
                      "description": "Koncert domacih pjevaca",
                      "name": "Samo domace",
                      "startDate": "2023-12-31T10:34:13.591",
                      "endDate": "2023-12-31T10:34:13.591"
                    }
                  ],
                  "success": true,
                  "message": "Concerts retrieved successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/organizer/13")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Concerts retrieved successfully"))
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
}