package com.gowil.zzleep.data.rest

import android.util.Log
import com.gowil.zzleep.data.entity.response.CulqiCreateTokenResponse
import com.gowil.zzleep.data.entity.response.ProductsVideoResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit




class ApiClient {
    private val TAG = "ApliCliente"

    var culqiV2Interface: CulqiV2Interface? = null
    var zzleepTokenInterface: ZzleepTokenInterface? = null


    /**
     * CULQI DEV
     **/

    var CULQI_KEY_PUBLIC = "Bearer pk_test_KKrFuZHtlqZdjPT7";
    var CULQI_KEY_PRIVATE = "Bearer sk_test_jqOmhclYvoLydHbK";


    val HOST_CULQI_V2 = "https://api.culqi.com/v2/"
    val AUTHORIZATION_CULQI_KEY_PUBLIC = CULQI_KEY_PUBLIC
    val AUTHORIZATION_CULQI_KEY_PRIVATE = CULQI_KEY_PRIVATE

    val TOKEN = "tokens"
    val CARGOS = "cargos"
    val CLIENTE = "cliente"
    val TARJETA = "tarjeta"

    val HOST = "http://104.236.201.66:8090/"

    fun getCulqiV2Interface(type: String): CulqiV2Interface? {
        var header = ""
        when (type) {
            TOKEN -> header = AUTHORIZATION_CULQI_KEY_PUBLIC
            CARGOS, CLIENTE, TARJETA -> header = AUTHORIZATION_CULQI_KEY_PRIVATE
        }
        val retrofit = Retrofit.Builder()
                .baseUrl(HOST_CULQI_V2)
                .client(getTokenClientInterceptor(header))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        culqiV2Interface = retrofit.create(CulqiV2Interface::class.java)
        return culqiV2Interface
    }

    fun getZzleepTokenInterface(typeHost: String, HEADER_TOKEN: String): ZzleepTokenInterface? {
        val host: String
        when (typeHost) {
            HOST -> host = HOST
            else -> host = HOST
        //else -> host = HOST_DEV_OALFARO
        }
        val retrofit = Retrofit.Builder()
                .baseUrl(host)
                .client(getTokenClientInterceptor(HEADER_TOKEN))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        zzleepTokenInterface = retrofit.create(ZzleepTokenInterface::class.java)
        return zzleepTokenInterface
    }


    interface CulqiV2Interface {
        @POST("tokens")
        fun createToken(@Body raw: Any): Call<CulqiCreateTokenResponse>

        /*@POST("charges")
        fun createCharges(@Body raw: Any): Call<CulqiChargesResponse>

        @POST("customers")
        fun createCustomers(@Body raw: Any): Call<CulqiCreateCustomerResponse>

        @POST("cards")
        fun createcards(@Body raw: Any): Call<CulqiCreateCardResponse>*/

    }

    interface ZzleepTokenInterface {
        @GET("/product/1")
        fun getVideo(): Call<ProductsVideoResponse>
    }


    fun getBasicClientInterceptor(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        // Add the interceptor to OkHttpClient
        val builder = OkHttpClient.Builder()

        //builder.interceptors().add(new LoggingIntercep    tor());
        builder.interceptors().add(logging)
        return builder.build()
    }

    fun getTokenClientInterceptor(token: String): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.writeTimeout(15, TimeUnit.SECONDS);
        builder.connectTimeout(15, TimeUnit.SECONDS);

        //builder.interceptors().add(new LoggingInterceptor());
        builder.interceptors().add(Interceptor { chain ->
            val original = chain.request()
            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("x-access-token", token)
                    .method(original.method(), original.body())

            val request = requestBuilder.build()

            val response = chain.proceed(request)
            //return chain.proceed(request);

            val responseBody = response.body()
            val contentLength: Int = responseBody!!.contentLength().toInt()
            val bodySize = if (contentLength != -1) contentLength.toString() + "-byte" else "unknown-length"
            Log.v(TAG, "<-:::- " + response.code() + ' ' + response.message() + ' '
                    + response.request().url() + " (" + bodySize + " body - " + response.protocol() + ')')

            response
        })

        return builder.build()
    }

}