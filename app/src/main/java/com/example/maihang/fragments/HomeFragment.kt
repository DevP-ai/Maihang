package com.example.maihang.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.maihang.adapter.PopularMealAdapter
import com.example.maihang.api.RetrofitApiInstance
import com.example.maihang.databinding.FragmentHomeBinding
import com.example.maihang.model.CategoryMeals
import com.example.maihang.model.Meal
import com.example.maihang.model.MealList
import com.example.maihang.ui.MealActivity
import com.example.maihang.viewModel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
   private lateinit var binding: FragmentHomeBinding
   private lateinit var homeViewModel: HomeViewModel
   private lateinit var randomMeal:Meal
   private lateinit var popularMealAdapter: PopularMealAdapter
   companion object{
       const val MEAL_ID = "com.example.maihang.fragments.idMeal"
       const val MEAL_NAME = "com.example.maihang.fragments.nameMeal"
       const val MEAL_IMAGE = "com.example.maihang.fragments.imageMeal"
   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel=ViewModelProvider(this)[HomeViewModel::class.java]
        popularMealAdapter= PopularMealAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        RetrofitApiInstance.getApi().getRandomMeal().enqueue(object : Callback<MealList>{
//            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
//                if(response.body() !=null){
//                    val randomMeal=response.body()!!.meals[0]
//                    Glide.with(this@HomeFragment)
//                        .load(randomMeal.strMealThumb)
//                        .into(binding.randomMealImage)
//                    Log.d("TEST","meal id:${randomMeal.idMeal} and name:${randomMeal.strMeal}")
//                }
//            }
//
//            override fun onFailure(call: Call<MealList>, t: Throwable) {
//                Toast.makeText(context,t.message.toString(),Toast.LENGTH_SHORT).show()
//            }
//
//        })

        popularMealRecyclerView()

        homeViewModel.getRandomMeal()
        randomMealObserver()
        onRandomMealClick()

        homeViewModel.getPopularMeal()
        popularMealObserver()

        onPopularMealClick()

    }

    private fun onPopularMealClick() {
        popularMealAdapter.popularMealClick={ meal->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_IMAGE,meal.strMealThumb)

            startActivity(intent)
        }
    }

    private fun popularMealRecyclerView() {
        binding.pItemRecycler.apply {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter=popularMealAdapter
        }
    }

    private fun popularMealObserver() {
        homeViewModel.observePopularMealLiveData().observe(viewLifecycleOwner){
          popularMealAdapter.setMeal(it as ArrayList<CategoryMeals>)
        }
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_IMAGE,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun randomMealObserver() {
        homeViewModel.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { value ->
            Glide.with(this)
                .load(value.strMealThumb)
                .into(binding.randomMealImage)

            this.randomMeal = value
        }
    }

}

