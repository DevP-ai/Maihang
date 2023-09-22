package com.example.maihang.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.maihang.R
import com.example.maihang.adapter.CategoryMealAdapter
import com.example.maihang.adapter.PopularMealAdapter
import com.example.maihang.databinding.FragmentHomeBinding
import com.example.maihang.model.Category
import com.example.maihang.model.MealsByCategory
import com.example.maihang.model.Meal
import com.example.maihang.ui.CategoryMealActivity
import com.example.maihang.ui.MainActivity
import com.example.maihang.ui.MealActivity
import com.example.maihang.viewModel.HomeViewModel

class HomeFragment : Fragment() {
   private lateinit var binding: FragmentHomeBinding
   private lateinit var viewModel: HomeViewModel
   private lateinit var randomMeal:Meal
   private lateinit var popularMealAdapter: PopularMealAdapter
   private lateinit var categoriesAdapter: CategoryMealAdapter
   companion object{
       const val MEAL_ID = "com.example.maihang.fragments.idMeal"
       const val MEAL_NAME = "com.example.maihang.fragments.nameMeal"
       const val MEAL_IMAGE = "com.example.maihang.fragments.imageMeal"
       const val CATEGORY_NAME="com.example.maihang.fragments.categoryName"
   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel=(activity as MainActivity).viewModel
        popularMealAdapter= PopularMealAdapter()
        categoriesAdapter= CategoryMealAdapter()

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


        viewModel.getRandomMeal()
        randomMealObserver()
        onRandomMealClick()

        viewModel.getPopularMeal()
        popularMealObserver()
        onPopularMealClick()


        categoriesRecyclerView()
        viewModel.getCategories()
        observeCategoryLivaData()

        onCategoryClick()

        onSearchIconClick()


    }

    private fun onSearchIconClick() {
        binding.searchBar.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick={category ->
            val intent=Intent(activity,CategoryMealActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun categoriesRecyclerView() {
        binding.categoryRecyclerView.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoriesAdapter
        }
    }

    private fun observeCategoryLivaData() {
        viewModel.observeMealCategoriesLiveData().observe(viewLifecycleOwner){
            categoriesAdapter.setCategories(it as ArrayList<Category>)
        }

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
        viewModel.observePopularMealLiveData().observe(viewLifecycleOwner){
          popularMealAdapter.setMeal(it as ArrayList<MealsByCategory>)
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
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { value ->
            Glide.with(this)
                .load(value.strMealThumb)
                .into(binding.randomMealImage)

            this.randomMeal = value
        }
    }

}

