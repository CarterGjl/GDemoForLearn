package com.tution.wanandroidkotlin.http

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tution.wanandroidkotlin.api.WanService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://wanandroid.com/"
    val reqApi: WanService by lazy {

        val interceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BASIC)
                .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient()
                .newBuilder()
                .addInterceptor(interceptor)
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        return@lazy retrofit.create(WanService::class.java)
    }
}