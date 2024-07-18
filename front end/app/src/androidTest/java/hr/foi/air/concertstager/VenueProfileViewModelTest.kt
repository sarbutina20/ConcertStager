package hr.foi.air.concertstager

import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VenueProfileViewModelTest {
    private lateinit var mockWebServer: MockWebServer

    @Test
    fun getVenue_VenueNotExists_ReturnErrorResponse() {
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

        val baseUrl = mockWebServer.url("/Venue/10")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Venue not found"))
        }
    }

    @Test
    fun getVenue_VenueExists_ReturnSuccessResponse() {
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

    @Test
    fun getAllUsers_UsersExists_ReturnSuccessResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 1,
                      "googleId": "qwertz",
                      "name": "Petra",
                      "email": "petra@gmail.com",
                      "password": "petra123",
                      "roleId": 1
                    },
                    {
                      "id": 5,
                      "googleId": "sllaslasl",
                      "name": "Aleksandar Trajkovski",
                      "email": "alex@gmail.com",
                      "password": "aleks12345",
                      "roleId": 1
                    }
                 ]
                 "success": true,
                 "message": "Successfully retrieved all users."
              }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/User")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Successfully retrieved all users."))
        }
    }

    @Test
    fun getVenueConcerts_VenueNotExists_ReturnErrorResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "Venue with this id doesn't exist",
                  "success": false,
                  "message": "Venue with this id doesn't exist"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("Concert/Venue/10")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Venue with this id doesn't exist"))
        }
    }

    @Test
    fun getVenueConcerts_NoConcertsForThisVenue_ReturnSuccessResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [],
                  "success": true,
                  "message": "Successfully retrieved concerts for this venue."
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("Concert/Venue/2")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Successfully retrieved concerts for this venue."))
        }
    }

    @Test
    fun getVenueConcerts_ExistsConcertForThisVenue_ReturnSuccessResponse() {
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
                  "message": "Venue concerts"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("Concert/Venue/1")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Venue concerts"))
        }
    }

    @Test
    fun getVenueReviews_VenueNotExists_ReturnErrorResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "Venue with this id doesn't exist",
                  "success": false,
                  "message": "Venue with this id doesn't exist"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/VenueReview/10")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Venue with this id doesn't exist"))
        }
    }

    @Test
    fun getVenueConcerts_NoReviewForThisVenue_ReturnSuccessResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [],
                  "success": true,
                  "message": "Successfully retrieved reviews for this venue."
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/VenueReview/2")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Successfully retrieved reviews for this venue."))
        }
    }

    @Test
    fun getVenueReviews_ExistsReviewsForThisVenue_ReturnSuccessResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 24,
                      "venueId": 1,
                      "grade": 1,
                      "description": "",
                      "userReviewId": 6
                    },
                    {
                      "id": 30,
                      "venueId": 1,
                      "grade": 1,
                      "description": "",
                      "userReviewId": 7
                    }
                  ],
                  "success": true,
                  "message": "Successfully retrieved all reviews for this venue."
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/VenueReview/1")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Successfully retrieved all reviews for this venue."))
        }
    }
}