package com.oaojjj.go_trip.util

import com.oaojjj.go_trip.model.PostDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("hakjin/getPostList.php")
    fun getPostList(
        @Query("user_id")user_id: Int
//        @Query("latitude")latitude:Double,
//        @Query("longitude")longitude:Double
    ): Call<List<PostDTO>>
}