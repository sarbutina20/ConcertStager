package hr.foi.air.concertstager.core.login.network

import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object OkHttpClientWithSSL {

    private val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            // Accept all client certificates
        }

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            // Accept all server certificates
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return emptyArray()
        }
    })

    private val sslContext = SSLContext.getInstance("SSL").apply {
        init(null, trustAllCerts, SecureRandom())
    }

    // Lazy init
    private val client: OkHttpClient by lazy {
        val sslSocketFactory = sslContext.socketFactory
        OkHttpClient.Builder()
            .hostnameVerifier { _, _ -> true }
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .build()
    }

    fun getOkHttpClientWithSSLClearance(): OkHttpClient {
        return client
    }
}