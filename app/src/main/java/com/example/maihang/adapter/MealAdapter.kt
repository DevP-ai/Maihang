package com.example.maihang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.maihang.databinding.MealItemBinding
import com.example.maihang.model.Meal

class MealAdapter :RecyclerView.Adapter<MealAdapter.FavouriteMealViewHolder>(){

    inner class FavouriteMealViewHolder(val binding:MealItemBinding):RecyclerView.ViewHolder(binding.root)

    private val diffUtil=object :DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem==newItem
        }
    }
    val differ=AsyncListDiffer(this,diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteMealViewHolder {
        return FavouriteMealViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavouriteMealViewHolder, position: Int) {
        val meal=differ.currentList[position]

        holder.binding.tvMealName.text=meal.strMeal

        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgMeal)

    }
}