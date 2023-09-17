package com.example.maihang.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maihang.api.RetrofitApiInstance
import com.example.maihang.db.MealDatabase
import com.example.maihang.model.Meal
import com.example.maihang.model.MealList
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.launch
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response

class MealActivityViewModel(private val mealDatabase: MealDatabase):ViewModel() {
    private var mealDetailsLiveData= MutableLiveData<Meal>()

    fun getMealById(id:String){
        RetrofitApiInstance.getApi().getMealById(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
               if (response.body() !=null){
                   mealDetailsLiveData.value=response.body()!!.meals[0]
               }else{
                   return
               }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {

            }

        })
    }

    fun observeMealDetails():LiveData<Meal>{
        return mealDetailsLiveData
    }

//    suspend fun updateMeal(meal: Meal){
//        mealDatabase.mealDao().updateMeal(meal)
//    }

    fun updateMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().updateMeal(meal)
        }
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().updateMeal(meal)
        }
    }
}