package com.example.maihang.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.maihang.adapter.MealAdapter
import com.example.maihang.databinding.FragmentFavouriteBinding
import com.example.maihang.ui.MainActivity
import com.example.maihang.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar


class FavouriteFragment : Fragment() {

   private lateinit var binding:FragmentFavouriteBinding
   private lateinit var viewModel: HomeViewModel
   private lateinit var mealAdapter: MealAdapter
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

        val itemTouchHelper=object :ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )=true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                viewModel.deleteMeal(mealAdapter.differ.currentList[position])

                Snackbar.make(requireView(),"Item Deleted",Snackbar.LENGTH_SHORT).setAction(
                    "UNDO",
                    View.OnClickListener {
                        viewModel.updateMeal(mealAdapter.differ.currentList[position])
                    }
                ).show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favRecyclerView)
    }

    private fun prepareRecyclerView() {
       mealAdapter= MealAdapter()
        binding.favRecyclerView.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=mealAdapter
        }
    }

    private fun observeFavData() {
        viewModel.observerFavMealLiveData().observe(viewLifecycleOwner, Observer {meals->
            mealAdapter.differ.submitList(meals)
        })
    }

}