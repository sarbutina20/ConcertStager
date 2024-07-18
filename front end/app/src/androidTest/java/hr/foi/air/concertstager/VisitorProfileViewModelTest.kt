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
class VisitorProfileViewModelTest {
    private lateinit var mockWebServer: MockWebServer

    @Test
    fun updateVisitor_VisitorNotFound_ReturnErrorResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "Visitor not updated",
                  "success": false,
                  "message": "Visitor not updated"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Visitor/5")

        val userUpdateBody = """
                {
                  "name": "Sven",
                  "email": "sven@gmail.com",
                  "password": "sven123"
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = userUpdateBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).patch(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Visitor not updated"))
        }
    }

    @Test
    fun updateVisitor_ExistedEmail_ReturnErrorResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "errorCode": 400,
                  "errorMessage": "Visitor not updated",
                  "success": false,
                  "message": "Visitor not updated"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Visitor/5")

        val userUpdateBody = """
                {
                  "name": "Sven",
                  "email": "karla@gmail.com",
                  "password": "sven123"
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = userUpdateBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).patch(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Visitor not updated"))
        }
    }

    @Test
    fun updateVisitor_ValidVisitorProperties_ReturnSuccessfulResponse() {
        mockWebServer = MockWebServer()

        val responseBody = """
                {
                  "data": [
                    {
                      "id": 7,
                      "googleId": "soqwowqwqpwq",
                      "name": "Sven",
                      "email": "sven@gmail.com",
                      "password": "sven123",
                      "roleId": 3
                    }
                  ],
                  "success": true,
                  "message": "Visitor updated successfully"
                }
            """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        mockWebServer.start()

        val baseUrl = mockWebServer.url("/Visitor/7")

        val userUpdateBody = """
                {
                  "name": "Sven",
                  "email": "sven@gmail.com",
                  "password": "sven123"
                }
            """.trimIndent()

        val client = OkHttpClient()

        val requestBody = userUpdateBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = okhttp3.Request.Builder().url(baseUrl).patch(requestBody).build()

        client.newCall(request).execute().body.also { res ->
            val stringResponseBody = res?.string()
            Assert.assertTrue(stringResponseBody!!.contains("Visitor updated successfully"))
        }
    }

}