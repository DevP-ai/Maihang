package com.example.maihang.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitApiInstance {
    private var mealApi: MealApi? = null

    fun getApi(): MealApi {
        if (mealApi == null) {
            mealApi = createMealApi()
        }
        return mealApi!!
    }

    private fun createMealApi(): MealApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(MealApi::class.java)
    }
}
