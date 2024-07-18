package hr.foi.air.concertstager

import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class PerformerProfileViewModelTest {
    private lateinit var mockWebServer: MockWebServer
    @Test
    fun updatePerformer_PerformerNotFound_ReturnErrorResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "Performer not updated",
                  "success": false,
                  "message": "Performer not updated"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Performer/20")

        val userUpdateBody = """
                {
                  "genreId": 3,
                  "name": "Karla",
                  "email": "karla@gmail.com",
                  "password": "karla123",
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = userUpdateBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).patch(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            assertTrue(stringResponseBody!!.contains("Performer not updated"))
        }
    }
    @Test
    fun updatePerformer_ValidPerformer_ReturnSuccessResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 6,
                      "genreId": 3,
                      "googleId": "oiowoqoqw",
                      "name": "Karla",
                      "email": "karla@gmail.com",
                      "password": "karla123",
                      "roleId": 2
                    }
                  ],
                  "success": true,
                  "message": "Performer updated successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Performer/6")

        val userUpdateBody = """
                {
                  "genreId": 3,
                  "name": "Karla",
                  "email": "karla@gmail.com",
                  "password": "karla123",
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = userUpdateBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).patch(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            assertTrue(stringResponseBody!!.contains("Performer updated successfully"))
        }
    }

    @Test
    fun getPerformer_PerformerNotFound_ReturnErrorResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "Performer not exists",
                  "success": false,
                  "message": "Performer not exists"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Performer/20")

        val client = OkHttpClient()
        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            assertTrue(stringResponseBody!!.contains("Performer not exists"))
        }
    }

    @Test
    fun getPerformer_PerformerFound_ReturnSuccessResponse() {
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
                  "message": "Performer found successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Performer/10")

        val client = OkHttpClient()
        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            assertTrue(stringResponseBody!!.contains("Performer found successfully"))
        }
    }

    @Test
    fun getGenre_InvalidGenre_ReturnErrorResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "Genre not found",
                  "success": false,
                  "message": "Genre not found"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Genre/20")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { response ->
            val stringResponseBody = response?.string()
            assertTrue(stringResponseBody!!.contains("Genre not found"))
        }
    }

    @Test
    fun getGenre_ValidGenre_ReturnSuccessResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                    "data": [
                            {
                                "id": 1,
                                "name": "Pop"
                            }
                            ],
                    "success": true,
                    "message": "Genre found"
}
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Genre/1")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { response ->
            val stringResponseBody = response?.string()
            assertTrue(stringResponseBody!!.contains("Genre found"))
        }
    }
    @Test
    fun getPerformerConcerts_InvalidUser_ReturnErrorResponse() {
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

        val baseUrl = mockWebServer.url("/Concert/performer/3/accepted")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { response ->
            val stringResponseBody = response?.string()
            assertTrue(stringResponseBody!!.contains("User with this id doesn't exist or is not a performer"))
        }
    }

    @Test
    fun getPerformerConcerts_UserHasNoConcerts_ReturnSuccessResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [],
                  "success": true,
                  "message": "Performer concerts"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/performer/16/accepted")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { response ->
            val stringResponseBody = response?.string()
            assertTrue(stringResponseBody!!.contains("Performer concerts"))
        }
    }

    @Test
    fun getPerformerConcerts_UserHasConcerts_ReturnSuccessResponse() {
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
                      "id": 5,
                      "venueId": 4,
                      "userId": 15,
                      "description": "Dobar koncert",
                      "name": "Koncert X",
                      "startDate": "2023-12-31T12:34:04.207",
                      "endDate": "2023-12-31T12:34:04.207"
                    },
                    {
                      "id": 7,
                      "venueId": 1,
                      "userId": 9,
                      "description": "Testni koncert",
                      "name": "Test",
                      "startDate": "2024-01-04T17:00:25.521",
                      "endDate": "2024-01-04T17:00:25.521"
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

        client.newCall(request).execute().body.also { response ->
            val stringResponseBody = response?.string()
            assertTrue(stringResponseBody!!.contains("Performer concerts"))
        }
    }

    @Test
    fun getVenues_VenuesExists_ReturnSuccessResponse() {
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

        client.newCall(request).execute().body.also { response ->
            val stringResponseBody = response?.string()
            assertTrue(stringResponseBody!!.contains("Venues found successfully"))
        }
    }

    @Test
    fun getVenues_VenuesNotExists_ReturnSuccessResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [],
                  "success": true,
                  "message": "Venues found successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Venue")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { response ->
            val stringResponseBody = response?.string()
            assertTrue(stringResponseBody!!.contains("Venues found successfully"))
        }
    }

    @Test
    fun getAllUsers_UsersNotExists_ReturnSuccessResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [],
                  "success": true,
                  "message": "Successfully retrieved all users."
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/User")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { response ->
            val stringResponseBody = response?.string()
            assertTrue(stringResponseBody!!.contains("Successfully retrieved all users."))
        }
    }

    @Test
    fun getAllUsers_UsersExists_ReturnSuccessResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                "data": [
                  {
                        "id": 26,
                        "googleId": "ksksaidasaiwiqwq",
                        "name": "Darko",
                        "email": "darko@gmail.com",
                        "password": "yecwHkT43QOQlOmwEu.re.lZPglaRypXiOriHH74fJ7utxBimjri",
                        "roleId": 1
                      },
                      {
                        "id": 27,
                        "googleId": "kslsoqolsd",
                        "name": "Aleksandra",
                        "email": "aleksandra@gmail.com",
                        "password": "Il9gM9WYZGAedcG9AAWJDf/f1IWq",
                        "roleId": 1
                      }
                    ],
                    "success": true,
                    "message": "Successfully retrieved all users."
                  }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/User")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { response ->
            val stringResponseBody = response?.string()
            assertTrue(stringResponseBody!!.contains("Successfully retrieved all users."))
        }
    }

    @Test
    fun getPerformerReviews_InvalidUser_ReturnErrorResponse() {
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

        val baseUrl = mockWebServer.url("/PerformerReview/{id}")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { response ->
            val stringResponseBody = response?.string()
            assertTrue(stringResponseBody!!.contains("Performer not found."))
        }
    }

    @Test
    fun getPerformerReviews_PerformerHasNoReviews_ReturnSuccessResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [],
                  "success": true,
                  "message": "Successfully retrieved reviews for this performer."
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/PerformerReview/11")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { response ->
            val stringResponseBody = response?.string()
            assertTrue(stringResponseBody!!.contains("Successfully retrieved reviews for this performer."))
        }
    }

    @Test
    fun getPerformerReviews_PerformerHasReviews_ReturnSuccessResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 7,
                      "userId": 10,
                      "grade": 5,
                      "description": "Odlican",
                      "userReviewId": 5
                    },
                    {
                      "id": 22,
                      "userId": 10,
                      "grade": 3,
                      "description": "",
                      "userReviewId": 6
                    },
                    {
                      "id": 31,
                      "userId": 10,
                      "grade": 1,
                      "description": "",
                      "userReviewId": 7
                    }
                  ],
                  "success": true,
                  "message": "Successfully retrieved reviews for this performer."
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/PerformerReview/10")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { response ->
            val stringResponseBody = response?.string()
            assertTrue(stringResponseBody!!.contains("Successfully retrieved reviews for this performer."))
        }
    }
}