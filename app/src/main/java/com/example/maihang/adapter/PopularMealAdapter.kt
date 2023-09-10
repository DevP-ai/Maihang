package com.example.maihang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.maihang.databinding.PopularItemBinding
import com.example.maihang.model.CategoryMeals
import com.example.maihang.model.MealList

class PopularMealAdapter():RecyclerView.Adapter<PopularMealAdapter.PopularMealViewHolder>() {

    private var mealList=ArrayList<CategoryMeals>()

    fun setMeal(mealList:ArrayList<CategoryMeals>){
        this.mealList=mealList
        notifyDataSetChanged()
    }

    inner class PopularMealViewHolder(val binding:PopularItemBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return  mealList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
       Glide.with(holder.itemView)
           .load(mealList[position].strMealThumb)
           .into(holder.binding.popularImage)
    }
}