package com.example.maihang.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.maihang.R
import com.example.maihang.adapter.CatAdapter
import com.example.maihang.adapter.CategoryMealAdapter
import com.example.maihang.api.RetrofitApiInstance
import com.example.maihang.databinding.ActivityCategoryMealBinding
import com.example.maihang.databinding.ActivityMealBinding
import com.example.maihang.fragments.HomeFragment
import com.example.maihang.model.CategoryList
import com.example.maihang.viewModel.CategoryMealViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCategoryMealBinding
    private lateinit var categoryMealViewModel: CategoryMealViewModel
    private lateinit var catAdapter: CatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCategoryMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMealViewModel=ViewModelProvider(this)[CategoryMealViewModel::class.java]
        categoryMealViewModel.getMealByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMealViewModel.observeCategory().observe(this, Observer {mealList->
            catAdapter.setMeal(mealList)
            binding.tvCountCategory.text="Total: ${mealList.size.toString()}"

        })

    }

    private fun prepareRecyclerView() {
        catAdapter= CatAdapter()
        binding.catRecyclerView.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=catAdapter
        }
    }


}