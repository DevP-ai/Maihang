package com.example.maihang.api

import com.example.maihang.model.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {

    @GET("/random.php")
    suspend fun getRandomMeal():Call<MealList>
}