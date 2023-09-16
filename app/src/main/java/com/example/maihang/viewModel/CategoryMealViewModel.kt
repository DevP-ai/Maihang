package com.example.maihang.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.maihang.api.RetrofitApiInstance
import com.example.maihang.model.CategoryList
import com.example.maihang.model.MealsByCategory
import com.example.maihang.model.MealsByCategoryList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealViewModel : ViewModel() {
    private var categoryListLiveData = MutableLiveData<List<MealsByCategory>>()
    fun getMealByCategory(categoryName: String) {
        RetrofitApiInstance.getApi().getMealByCategory(categoryName)
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    response.body().let {mealList->
                       categoryListLiveData.postValue(mealList!!.meals)
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun observeCategory(): LiveData<List<MealsByCategory>> {
        return categoryListLiveData
    }
}