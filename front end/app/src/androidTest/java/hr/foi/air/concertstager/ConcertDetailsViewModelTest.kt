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
class ConcertDetailsViewModelTest {
    private lateinit var mockWebServer: MockWebServer

    @Test
    fun getConcert_ConcertDoesNotExists_ErrorResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "Concert with this id doesn't exist",
                  "success": false,
                  "message": "Concert with this id doesn't exist"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/21")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Concert with this id doesn't exist"))
        }
    }

    @Test
    fun getConcert_ConcertExists_SuccessResponse(){
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
                  "message": "Concert retrieved successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/4")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Concert retrieved successfully"))
        }
    }

    @Test
    fun getOrganizer_OrganizerDoesNotExists_ErrorResponse(){
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

        val baseUrl = mockWebServer.url("/Organizer/27")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Organizer not exists"))
        }
    }

    @Test
    fun getOrganizer_OrganizerExists_SuccessResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 21,
                      "contactNumber": "0912821252",
                      "googleId": "nnsauusapwoas",
                      "name": "Luka Jelenic",
                      "email": "jela@gmail.com",
                      "password": "lukajela26",
                      "roleId": 1
                    }
                  ],
                  "success": true,
                  "message": "Organizer found successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Organizer/21")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Organizer found successfully"))
        }
    }
    @Test
    fun getVenue_VenueDoesNotExists_ErrorResponse(){
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

        val baseUrl = mockWebServer.url("/Venue/33")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Venue not found"))
        }
    }

    @Test
    fun getVenue_VenueExists_SuccessResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 3,
                      "userId": 5,
                      "name": "Nogometno igraliste",
                      "decription": "Igraliste u Zagrebu",
                      "city": "Zagreb",
                      "address": "Kranjceviceva 19",
                      "capacity": 25000
                    }
                  ],
                  "success": true,
                  "message": "Venue found successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Venue/3")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Venue found successfully"))
        }
    }

    @Test
    fun getPerformersForConcerts_NoAcceptedPerformers_ErrorResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "No performers found for this concert",
                  "success": false,
                  "message": "No performers found for this concert"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/3/performer")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("No performers found for this concert"))
        }
    }

    @Test
    fun getPerformersForConcerts_HasAcceptedPerformers_SuccessResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 10,
                      "genreId": 10,
                      "googleId": "kskazsjvma",
                      "name": "Dusko Lokin",
                      "email": "mlaged@gmail.com",
                      "password": "dusko123",
                      "roleId": 2
                    }
                  ],
                  "success": true,
                  "message": "Performers for concert"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/10/performer")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Performers for concert"))
        }
    }

    @Test
    fun getAllReviewsOfUser_UserNotExists_ErrorResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 404,
                  "errorMessage": "User not found.",
                  "success": false,
                  "message": "User not found."
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/PerformerReview/user/3")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("User not found."))
        }
    }

    @Test
    fun getAllReviewsOfUser_UserHasNoReviews_ErrorResponse(){
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

        val baseUrl = mockWebServer.url("/PerformerReview/user/10")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Successfully retrieved reviews for this user."))
        }
    }

    @Test
    fun getAllReviewsOfUser_UserHasReviews_SuccessResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 22,
                      "grade": 3,
                      "description": "",
                      "userReviewId": 6,
                      "userId": 10,
                      "venueId": 0
                    },
                    {
                      "id": 24,
                      "grade": 1,
                      "description": "",
                      "userReviewId": 6,
                      "userId": 0,
                      "venueId": 1
                    }
                  ],
                  "success": true,
                  "message": "Successfully retrieved reviews for this user."
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/PerformerReview/user/6")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Successfully retrieved reviews for this user."))
        }
    }

    @Test
    fun createConcertEntry_ConcertNotExists_ReturnErrorResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "Concert with this id doesn't exist",
                  "success": false,
                  "message": "Concert with this id doesn't exist"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/entry")

        val createConcertEntryBody = """
                {
                  "userId": 5,
                  "concertId": 20
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = createConcertEntryBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).post(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Concert with this id doesn't exist"))
        }
    }

    @Test
    fun createConcertEntry_UserIsNotPerformer_ReturnErrorResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "User with this id doesn't exist or is not a performer",
                  "success": false,
                  "message": "User with this id doesn't exist or is not a performer"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/entry")

        val createConcertEntryBody = """
                {
                  "userId": 5,
                  "concertId": 20
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = createConcertEntryBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).post(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("User with this id doesn't exist or is not a performer"))
        }
    }

    @Test
    fun createConcertEntry_ConcertEntryAlreadyExists_ReturnErrorResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "Concert entry with this user and concert already exists",
                  "success": false,
                  "message": "Concert entry with this user and concert already exists"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/entry")

        val createConcertEntryBody = """
                {
                  "userId": 10,
                  "concertId": 2
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = createConcertEntryBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).post(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Concert entry with this user and concert already exists"))
        }
    }

    @Test
    fun createConcertEntry_AllValid_ReturnSuccessResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 19,
                      "date": "2024-01-13T19:13:47.9266059+01:00",
                      "isAccepted": null,
                      "userId": 10,
                      "concertId": 4
                    }
                  ],
                  "success": true,
                  "message": "Concert entry created successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/entry")

        val createConcertEntryBody = """
                {
                  "userId": 10,
                  "concertId": 4
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = createConcertEntryBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).post(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Concert entry created successfully"))
        }
    }

    @Test
    fun getConcertEntries_ConcertNotExists_ErrorResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "Concert with this id does not exists",
                  "success": false,
                  "message": "Concert with this id does not exists"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/30/entry")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Concert with this id does not exists"))
        }
    }

    @Test
    fun getConcertEntries_ConcertEntriesExists_SuccessResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 8,
                      "date": "2024-01-03T16:02:48.2683852",
                      "isAccepted": true,
                      "userId": 10,
                      "concertId": 2
                    },
                    {
                      "id": 15,
                      "date": "2024-01-07T17:17:05.3958545",
                      "isAccepted": true,
                      "userId": 11,
                      "concertId": 2
                    }
                  ],
                  "success": true,
                  "message": "Concert entries retrieved successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/2/entry")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Concert entries retrieved successfully"))
        }
    }
}