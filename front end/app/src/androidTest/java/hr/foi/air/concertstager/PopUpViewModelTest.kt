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
class PopUpViewModelTest {
    private lateinit var mockWebServer: MockWebServer

    @Test
    fun reviewPerformer_PerformerNotExists_ReturnErrorResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 404,
                  "errorMessage": "Performer not found.",
                  "success": false,
                  "message": "Performer not found."
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/PerformerReview")

        val performerReviewCreateBody = """
                {
                  "grade": 5,
                  "description": "Opis",
                  "userReviewId": 6,
                  "userId": 5
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = performerReviewCreateBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).post(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Performer not found."))
        }
    }

    @Test
    fun reviewPerformer_ReviewAlreadyExists_ReturnErrorResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 403,
                  "errorMessage": "Review already exists.",
                  "success": false,
                  "message": "Review already exists."
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/PerformerReview")

        val performerReviewCreateBody = """
                {
                  "grade": 5,
                  "description": "Opis",
                  "userReviewId": 6,
                  "userId": 1
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = performerReviewCreateBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).post(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Review already exists."))
        }
    }

    @Test
    fun reviewPerformer_ReviewOk_ReturnSuccessResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 34,
                      "userId": 11,
                      "grade": 5,
                      "description": "Opis",
                      "userReviewId": 7
                    }
                  ],
                  "success": true,
                  "message": "Successfully created review for this performer."
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/PerformerReview")

        val performerReviewCreateBody = """
                {
                  "grade": 5,
                  "description": "Opis",
                  "userReviewId": 7,
                  "userId": 11
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = performerReviewCreateBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).post(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Successfully created review for this performer."))
        }
    }

    @Test
    fun reviewVenue_VenueNotExists_ReturnErrorResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 404,
                  "errorMessage": "Venue not found.",
                  "success": false,
                  "message": "Venue not found."
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/VenueReview")

        val venueReviewCreateBody = """
                {
                  "grade": 5,
                  "description": "Opis",
                  "userReviewId": 6,
                  "venueId": 20
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = venueReviewCreateBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).post(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Venue not found."))
        }
    }

    @Test
    fun reviewVenue_ReviewAlreadyExists_ReturnErrorResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 403,
                  "errorMessage": "Review already exists.",
                  "success": false,
                  "message": "Review already exists."
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/VenueReview")

        val venueReviewCreateBody = """
                {
                  "grade": 5,
                  "description": "Opis",
                  "userReviewId": 6,
                  "venueId": 1
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = venueReviewCreateBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).post(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Review already exists."))
        }
    }

    @Test
    fun reviewVenue_ReviewOk_ReturnSuccessResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 34,
                      "venueId": 1,
                      "grade": 5,
                      "description": "Opis",
                      "userReviewId": 7
                    }
                  ],
                  "success": true,
                  "message": "Successfully created review for this venue."
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/VenueReview")

        val venueReviewCreateBody = """
                {
                  "grade": 5,
                  "description": "Opis",
                  "userReviewId": 7,
                  "venueId": 1
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = venueReviewCreateBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).post(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Successfully created review for this venue."))
        }
    }
}