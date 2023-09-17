package com.example.maihang.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.maihang.R
import com.example.maihang.adapter.FavouriteMealAdapter
import com.example.maihang.databinding.FragmentFavouriteBinding
import com.example.maihang.ui.MainActivity
import com.example.maihang.viewModel.HomeViewModel


class FavouriteFragment : Fragment() {

   private lateinit var binding:FragmentFavouriteBinding
   private lateinit var viewModel: HomeViewModel
   private lateinit var favouriteMealAdapter: FavouriteMealAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentFavouriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeFavData()

        prepareRecyclerView()
    }

    private fun prepareRecyclerView() {
       favouriteMealAdapter= FavouriteMealAdapter()
        binding.favRecyclerView.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=favouriteMealAdapter
        }
    }

    private fun observeFavData() {
        viewModel.observerFavMealLiveData().observe(viewLifecycleOwner, Observer {meals->
            favouriteMealAdapter.differ.submitList(meals)
        })
    }

}