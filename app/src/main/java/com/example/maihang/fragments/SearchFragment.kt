package com.example.maihang.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maihang.adapter.MealAdapter
import com.example.maihang.databinding.FragmentSearchBinding
import com.example.maihang.ui.MainActivity
import com.example.maihang.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
   private lateinit var binding:FragmentSearchBinding
   private lateinit var viewModel: HomeViewModel
   private lateinit var searchMealAdapter: MealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel=(activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgArrow.setOnClickListener {
            searchMeals()

        }
        prepareRecyclerView()
        observeSearchData()

        var searchJob:Job?=null
        binding.edtSearch.addTextChangedListener {searchQuery->
            searchJob?.cancel()
            searchJob=lifecycleScope.launch {
                delay(500)
                viewModel.searchMeal(searchQuery.toString())
            }
        }
    }

    private fun observeSearchData() {
        viewModel.searchMealObserve().observe(viewLifecycleOwner, Observer {mealList->
            searchMealAdapter.differ.submitList(mealList)
        })
    }

    private fun searchMeals() {
        val searchQuery=binding.edtSearch.text.toString()
        if(searchQuery.isNotEmpty()){
            viewModel.searchMeal(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        searchMealAdapter= MealAdapter()
        binding.searchRecyclerView.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=searchMealAdapter
        }
    }

}