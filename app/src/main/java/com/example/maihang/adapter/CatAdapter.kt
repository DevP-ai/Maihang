package com.example.maihang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.maihang.databinding.CategoryItemBinding
import com.example.maihang.databinding.MealItemBinding
import com.example.maihang.model.MealsByCategory

class CatAdapter:RecyclerView.Adapter<CatAdapter.CatViewHolder>() {
    private var catList=ArrayList<MealsByCategory>()

    fun setMeal(catList:List<MealsByCategory>){
        this.catList=catList as ArrayList<MealsByCategory>

        notifyDataSetChanged()
    }
    inner class CatViewHolder(val binding:MealItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return catList.size
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.binding.tvMealName.text=catList[position].strMeal

        Glide.with(holder.itemView)
            .load(catList[position].strMealThumb)
            .into(holder.binding.imgMeal)
    }
}