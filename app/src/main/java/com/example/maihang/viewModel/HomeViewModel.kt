package com.example.maihang.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.maihang.api.RetrofitApiInstance
import com.example.maihang.model.Category
import com.example.maihang.model.CategoryList
import com.example.maihang.model.MealsByCategoryList
import com.example.maihang.model.MealsByCategory
import com.example.maihang.model.Meal
import com.example.maihang.model.MealList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel:ViewModel() {
    private var randomMealLiveData=MutableLiveData<Meal>()
    private var popularMealLiveData=MutableLiveData<List<MealsByCategory>>()
    private var mealCategoryLiveData=MutableLiveData<List<Category>>()
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
        RetrofitApiInstance.getApi().getPopularMeal("Seafood").enqueue(object :Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if(response.body() !=null){
                    popularMealLiveData.value= response.body()!!.meals
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getCategories(){
        RetrofitApiInstance.getApi().getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if(response.body() !=null){
                    mealCategoryLiveData.value=response.body()!!.categories
                }else{
                    return
                }
                /*
                Another method we can use
                response.body().let{ categoryList ->
                      mealCategoryLiveData.postValue(categoryList.categories)
                }

                 */
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }

    fun observePopularMealLiveData(): LiveData<List<MealsByCategory>> {
        return popularMealLiveData
    }

    fun observeMealCategoriesLiveData(): LiveData<List<Category>>{
        return mealCategoryLiveData
    }
}