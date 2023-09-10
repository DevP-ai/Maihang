package com.example.maihang.api

import com.example.maihang.model.CategoryList
import com.example.maihang.model.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealById(@Query("i")id:String):Call<MealList>

    @GET("filter.php?")
    fun getPopularMeal(@Query("c")categoryName:String):Call<CategoryList>

}