package hr.foi.air.concertstager

import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PerformerHomepageViewModelTest {
    private lateinit var mockWebServer: MockWebServer

    @Test
    fun getUpcomingConcerts_ThereHasNoUpcomingConcerts_ErrorResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "There are no upcoming concerts",
                  "success": false,
                  "message": "There are no upcoming concerts"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/Upcoming")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("There are no upcoming concerts"))
        }
    }

    @Test
    fun getUpcomingConcerts_ThereHasUpcomingConcerts_SuccessResponse(){
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
                    },
                    {
                      "id": 11,
                      "venueId": 4,
                      "userId": 5,
                      "description": "Koncert kod velikog mosta na Bracu",
                      "name": "Morski koncert",
                      "startDate": "2024-01-10T20:51:54.24",
                      "endDate": "2024-01-11T20:51:54.24"
                    }
                  ],
                  "success": true,
                  "message": "Upcoming concerts retrieved successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/Upcoming")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Upcoming concerts retrieved successfully"))
        }
    }

    @Test
    fun getPendingConcertsForPerformer_GivenUserThatIsNotPerformer_ErrorResponse(){
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

        val baseUrl = mockWebServer.url("/Concert/performer/23/pending")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("User with this id doesn't exist or is not a performer"))
        }
    }

    @Test
    fun getPendingConcertsForPerformer_PerformerDontHavePendingConcerts_ErrorResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "No concerts found for this performer.",
                  "success": false,
                  "message": "No concerts found for this performer."
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/performer/16/pending")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("No concerts found for this performer."))
        }
    }

    @Test
    fun getPendingConcertsForPerformer_PerformerHasPendingConcerts_SuccessResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 11,
                      "venueId": 4,
                      "userId": 5,
                      "description": "Koncert kod velikog mosta na Bracu",
                      "name": "Morski koncert",
                      "startDate": "2024-01-10T20:51:54.24",
                      "endDate": "2024-01-11T20:51:54.24"
                    }
                  ],
                  "success": true,
                  "message": "Performer pending concerts"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/performer/10/pending")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Performer pending concerts"))
        }
    }

    @Test
    fun getAcceptedConcertsForPerformer_GivenUserIsNotPerformerOrNotExists_ErrorResponse(){
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

        val baseUrl = mockWebServer.url("/Concert/performer/27/accepted")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("User with this id doesn't exist or is not a performer"))
        }
    }

    @Test
    fun getAcceptedConcertsForPerformer_UserDontHaveAcceptedConcerts_ErrorResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "No concert entries found for this performer.",
                  "success": false,
                  "message": "No concert entries found for this performer."
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/performer/16/accepted")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("No concert entries found for this performer."))
        }
    }

    @Test
    fun getAcceptedConcertsForPerformer_UserHasAcceptedConcerts_ErrorResponse(){
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
                      "id": 8,
                      "venueId": 4,
                      "userId": 9,
                      "description": "Zabavite se uz najvece slavonske hitove.",
                      "name": "Slavonija",
                      "startDate": "2024-01-07T18:31:31.916",
                      "endDate": "2024-01-08T18:31:31.916"
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
                  "message": "Performer concerts"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/performer/10/accepted")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Performer concerts"))
        }
    }
}