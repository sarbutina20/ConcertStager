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
class VisitorReviewPopUpViewModelTest {
    private lateinit var mockWebServer: MockWebServer

    @Test
    fun reviewVenue_AlreadyCreatedeReview_ReturnErrorResponse() {
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

        val venueReviewCreateBody = """
                {
                  "grade": 3,
                  "description": "Opis",
                  "userReviewId": 6,
                  "venueId": 1,
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
    fun reviewVenue_ReviewSuccessful_ReturnSuccessResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 32,
                      "venueId": 5,
                      "grade": 3,
                      "description": "Ok",
                      "userReviewId": 6
                    }
                  ],
                  "success": true,
                  "message": "Successfully created review for this venue."
                  }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/PerformerReview")

        val venueReviewCreateBody = """
                {
                  "grade": 3,
                  "description": "Opis",
                  "userReviewId": 6,
                  "venueId": 1,
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

    @Test
    fun reviewPerformer_AlreadyCreatedeReview_ReturnErrorResponse() {
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
                  "grade": 3,
                  "description": "Opis",
                  "userReviewId": 6,
                  "userId": 10,
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
    fun reviewPerformer_SuccessfulReview_ReturnSuccessResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 33,
                      "userId": 11,
                      "grade": 5,
                      "description": "recenzija",
                      "userReviewId": 6
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
                  "grade": 3,
                  "description": "Opis",
                  "userReviewId": 6,
                  "userId": 11,
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
}