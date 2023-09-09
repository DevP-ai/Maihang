package com.example.maihang.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.maihang.api.RetrofitApiInstance
import com.example.maihang.databinding.FragmentHomeBinding
import com.example.maihang.model.Meal
import com.example.maihang.model.MealList
import com.example.maihang.viewModel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
   private lateinit var binding: FragmentHomeBinding
   private lateinit var homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel=ViewModelProvider(this)[HomeViewModel::class.java]

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

        homeViewModel.getRandomMeal()
        randomMealObserver()

    }

    private fun randomMealObserver() {
        homeViewModel.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { value ->
            Glide.with(this)
                .load(value.strMealThumb)
                .into(binding.randomMealImage)
        }
    }

}

