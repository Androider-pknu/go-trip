package com.oaojjj.go_trip.util

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AWSRetrofit {
    private val gson =
        GsonBuilder()
            .setLenient()
            .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://wktpgl70.cafe24.com/") // aws ip address
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val retrofitAPI = retrofit.create(RetrofitAPI::class.java)

    fun getAPI(): RetrofitAPI = retrofitAPI
}