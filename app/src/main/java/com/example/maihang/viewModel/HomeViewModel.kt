package com.example.maihang.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.maihang.api.RetrofitApiInstance
import com.example.maihang.model.CategoryList
import com.example.maihang.model.CategoryMeals
import com.example.maihang.model.Meal
import com.example.maihang.model.MealList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel:ViewModel() {
    private var randomMealLiveData=MutableLiveData<Meal>()
    private var popularMealLiveData=MutableLiveData<List<CategoryMeals>>()
    fun getRandomMeal(){
        RetrofitApiInstance.getApi().getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() !=null){
                    val randomMeal=response.body()!!.meals[0]
                    randomMealLiveData.value=randomMeal
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {

            }

        })
    }

    fun getPopularMeal(){
        RetrofitApiInstance.getApi().getPopularMeal("Seafood").enqueue(object :Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if(response.body() !=null){
                    popularMealLiveData.value= response.body()!!.meals
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }

    fun observePopularMealLiveData(): LiveData<List<CategoryMeals>> {
        return popularMealLiveData
    }
}