package hr.foi.air.concertstager

import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VisitorHomepageViewModelTest {
    private lateinit var mockWebServer : MockWebServer

    @Test
    fun getUpcomingConcerts_NoUpcomingConcerts_ErrorResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "There are no upcoming concerts.",
                  "success": false,
                  "message": "There are no upcoming concerts."
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/upcoming")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("There are no upcoming concerts."))
        }
    }

    @Test
    fun getUpcomingConcerts_HasUpcomingConcerts_SuccessResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                      "data": [
                        {
                          "id": 8,
                          "venueId": 4,
                          "userId": 9,
                          "description": "Zabavite se uz najvece slavonske hitove.",
                          "name": "Slavonija",
                          "startDate": "2024-01-07T18:31:31.916",
                          "endDate": "2024-01-08T18:31:31.916"
                        },
                        {
                          "id": 9,
                          "venueId": 4,
                          "userId": 9,
                          "description": "Koncert u Zagrebu",
                          "name": "Domu mom",
                          "startDate": "2024-01-08T18:31:31.916",
                          "endDate": "2024-01-09T18:31:31.916"
                        },
                        {
                          "id": 10,
                          "venueId": 6,
                          "userId": 19,
                          "description": "Spektakl u Istri",
                          "name": "Napoznatije domace pjesme",
                          "startDate": "2024-01-09T13:10:31.601",
                          "endDate": "2024-01-10T13:10:31.601"
                        }
                      ],
                      "success": true,
                      "message": "Upcoming concerts retrieved successfully"
                    }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/upcoming")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Upcoming concerts retrieved successfully"))
        }
    }

    @Test
    fun getFinishedConcerts_NoFinishedConcerts_ErrorResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "No finished concerts found",
                  "success": false,
                  "message": "No finished concerts found"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/finished")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("No finished concerts found"))
        }
    }

    @Test
    fun getFinishedConcerts_HasFinishedConcerts_SuccessResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 2,
                      "venueId": 1,
                      "userId": 5,
                      "description": "Koncert legende hercegovacke muzike",
                      "name": "Mate Bulic",
                      "startDate": "2023-12-30T18:47:11.35",
                      "endDate": "2023-12-30T18:47:11.35"
                    },
                    {
                      "id": 3,
                      "venueId": 1,
                      "userId": 5,
                      "description": "Dobar neki koncert",
                      "name": "Koncert Jelene Rozge",
                      "startDate": "2023-12-30T20:58:10.768",
                      "endDate": "2023-12-30T20:58:10.768"
                    },
                    {
                      "id": 4,
                      "venueId": 4,
                      "userId": 13,
                      "description": "Koncert domacih pjevaca",
                      "name": "Samo domace",
                      "startDate": "2023-12-31T10:34:13.591",
                      "endDate": "2023-12-31T10:34:13.591"
                    },
                    {
                      "id": 5,
                      "venueId": 4,
                      "userId": 15,
                      "description": "Dobar koncert",
                      "name": "Koncert X",
                      "startDate": "2023-12-31T12:34:04.207",
                      "endDate": "2023-12-31T12:34:04.207"
                    },
                    {
                      "id": 6,
                      "venueId": 4,
                      "userId": 9,
                      "description": "Koncert na Poljudu",
                      "name": "Dalmatinski koncert",
                      "startDate": "2024-01-05T15:32:52.566",
                      "endDate": "2024-01-05T17:32:52.566"
                    },
                    {
                      "id": 7,
                      "venueId": 1,
                      "userId": 9,
                      "description": "Testni koncert",
                      "name": "Test",
                      "startDate": "2024-01-04T17:00:25.521",
                      "endDate": "2024-01-04T17:00:25.521"
                    }
                  ],
                  "success": true,
                  "message": "Finished concerts retrieved successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/finished")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Finished concerts retrieved successfully"))
        }
    }

    @Test
    fun getVenue_NoVenueWithThisId_ErrorResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 404,
                  "errorMessage": "Venue not found",
                  "success": false,
                  "message": "Venue not found"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Venue/27")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Venue not found"))
        }
    }

    @Test
    fun getVenue_ExistsVenueWithThisId_SuccessResponse(){
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
                    }
                  ],
                  "success": true,
                  "message": "Venue found successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Venue/1")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Venue found successfully"))
        }
    }
}