package com.example.maihang.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.maihang.R
import com.example.maihang.adapter.CategoryMealAdapter
import com.example.maihang.databinding.FragmentCategoriesBinding
import com.example.maihang.ui.CategoryMealActivity
import com.example.maihang.ui.MainActivity
import com.example.maihang.ui.MealActivity
import com.example.maihang.viewModel.HomeViewModel


class CategoriesFragment : Fragment() {
    private lateinit var binding:FragmentCategoriesBinding
    private lateinit var categoryMealAdapter: CategoryMealAdapter
    private lateinit var viewModel:HomeViewModel

    companion object{
        const val CATEGORY_NAME="com.example.maihang.fragments.categoryName"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel=(activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentCategoriesBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        observeCategory()

        onClickCategory()
    }

    private fun onClickCategory() {
        categoryMealAdapter.onItemClick={ category ->
            val intent=Intent(activity,CategoryMealActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun observeCategory() {
        viewModel.observeMealCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories->
           categoryMealAdapter.setCategories(categories)
        })
    }

    private fun prepareRecyclerView() {
        categoryMealAdapter= CategoryMealAdapter()
        binding.rvCategory.apply {
             layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoryMealAdapter
        }
    }

}