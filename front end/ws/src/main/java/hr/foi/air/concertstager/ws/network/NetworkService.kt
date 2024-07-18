package hr.foi.air.concertstager.ws.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object NetworkService {
    private const val BASE_URL = "https://192.168.5.83:7096/api/"

    val trustAllCerts = arrayOf<TrustManager>(
        object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {}

            override fun checkServerTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {}

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }
        }
    )


    val sslContext = SSLContext.getInstance("SSL")
        .apply {
            init(null, trustAllCerts, java.security.SecureRandom())
        }


    val okHttpClient = OkHttpClient.Builder()
        .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
        .hostnameVerifier(HostnameVerifier { _, _ -> true })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()


    var instance: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val performerService: PerformerService = instance.create(PerformerService::class.java)
    val authService: AuthenticationService = instance.create(AuthenticationService::class.java)
    val genreService: GenreService = instance.create(GenreService::class.java)
    val organizerService: OrganizerService = instance.create(OrganizerService::class.java)
    val visitorService: VisitorService = instance.create(VisitorService::class.java)
    val concertService: ConcertService = instance.create(ConcertService::class.java)
    val venueService: VenueService = instance.create(VenueService::class.java)
    val reviewService : ReviewService = instance.create(ReviewService::class.java)
    val googleSignInService : GoogleSignInService = instance.create(GoogleSignInService::class.java)
}