package com.gabo.condorsports.data.network

import com.gabo.condorsports.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {
    private const val BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/"
    val teamAPIService : TeamAPIService  by lazy {
        getRetrofit().create(TeamAPIService::class.java)
    }
    private fun getRetrofit(): Retrofit {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) { // development build
            logging.level = Level.BODY
        } else { // production build
            logging.level = Level.BASIC
        }

        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }
}