package org.diyorbek.mvvm_h1.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import org.diyorbek.mvvm_h1.util.Constantas.BASE_URL
import org.diyorbek.mvvm_h1.util.Constantas.API_KEY

object RetroInstance {
    fun retrofitInstance(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client())
            .build()
            .create(ApiService::class.java)
    }

    private fun client(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header(
                    "Authorization",
                    "$API_KEY"
                )
                chain.proceed(builder.build())
            })
            .build()
    }
}