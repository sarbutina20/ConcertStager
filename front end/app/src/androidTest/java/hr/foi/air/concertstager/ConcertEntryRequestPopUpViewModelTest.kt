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
class ConcertEntryRequestPopUpViewModelTest {
    private lateinit var mockWebServer: MockWebServer

    @Test
    fun getAllPerformers_PerformersNotExists_ErrorResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [],
                  "success": true,
                  "message": "Successfully retrieved performers."
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Performer")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Successfully retrieved performers."))
        }
    }

    @Test
    fun getAllPerformers_PerformersExists_SuccessResponse(){
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
                    },
                    {
                      "id": 11,
                      "genreId": 5,
                      "googleId": "oskamswqs",
                      "name": "Eva",
                      "email": "eva@gmail.com",
                      "password": "eva123",
                      "roleId": 2
                    },
                    {
                      "id": 16,
                      "genreId": 9,
                      "googleId": "ldsalsaakdas",
                      "name": "Drazen Zecic",
                      "email": "draz@gmail.com",
                      "password": "drazen123",
                      "roleId": 2
                    },
                    {
                      "id": 20,
                      "genreId": 3,
                      "googleId": "slaowlssv",
                      "name": "Mia Dimsic",
                      "email": "mia@gmail.com",
                      "password": "miadimsic123",
                      "roleId": 2
                    },
                    {
                      "id": 25,
                      "genreId": 1,
                      "googleId": "sakdksakdasks",
                      "name": "janko",
                      "email": "janko@gmail.com",
                      "password": "kskskadjasiasi",
                      "roleId": 2
                    }
                  ],
                  "success": true,
                  "message": "Performers found successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Performer")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Performers found successfully"))
        }
    }

    @Test
    fun acceptDenyEntry_EntryAccepted_ReturnSuccessfulResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 13,
                      "date": "2024-01-06T21:51:13.5143517",
                      "isAccepted": true,
                      "userId": 10,
                      "concertId": 3
                    }
                  ],
                  "success": true,
                  "message": "Concert entry updated successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("Concert/entry/5")

        val createConcertBody = """
                {
                  "isAccepted": true
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = createConcertBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).patch(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Concert entry updated successfully"))
        }
    }

    @Test
    fun acceptDenyEntry_EntryDeclined_ReturnSuccessfulResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 13,
                      "date": "2024-01-06T21:51:13.5143517",
                      "isAccepted": false,
                      "userId": 10,
                      "concertId": 3
                    }
                  ],
                  "success": true,
                  "message": "Concert entry updated successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("Concert/entry/7")

        val createConcertBody = """
                {
                  "isAccepted": true
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = createConcertBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).patch(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Concert entry updated successfully"))
        }
    }

    @Test
    fun acceptDenyEntry_EntryNotExists_ErrorResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "Concert entry with this id doesn't exist",
                  "success": false,
                  "message": "Concert entry with this id doesn't exist"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("Concert/entry/30")

        val createConcertEntryBody = """
                {
                  "isAccepted": true
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = createConcertEntryBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).patch(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Concert entry with this id doesn't exist"))
        }
    }

    @Test
    fun getUnresolvedEntries_EntriesNotExists_SuccessResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [],
                  "success": true,
                  "message": "Concert entries retrieved successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/1/Unresolvedentry")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Concert entries retrieved successfully"))
        }
    }

    @Test
    fun getUnresolvedEntries_EntriesExists_SuccessResponse(){
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 13,
                      "date": "2024-01-06T21:51:13.5143517",
                      "isAccepted": false,
                      "userId": 10,
                      "concertId": 3
                    }
                  ],
                  "success": true,
                  "message": "Concert entry retrieved successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Concert/1/Unresolvedentry")

        val client = OkHttpClient()

        val request = okhttp3.Request.Builder().url(baseUrl).get().build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Concert entry retrieved successfully"))
        }
    }
}