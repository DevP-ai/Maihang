package com.example.maihang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.maihang.R
import com.example.maihang.databinding.CategoryItemBinding
import com.example.maihang.model.Category
import com.example.maihang.model.CategoryList

class CategoryMealAdapter:RecyclerView.Adapter<CategoryMealAdapter.CategoryMealViewHolder>() {
    private var categoryList=ArrayList<Category>()
    var onItemClick: ((Category) -> Unit?)? =null

    fun setCategories(categoryList:List<Category>){
        this.categoryList=categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }

    inner class CategoryMealViewHolder(val binding:CategoryItemBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealViewHolder {
        return CategoryMealViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryMealViewHolder, position: Int) {
        holder.binding.mealCategoryName.text=categoryList[position].strCategory

        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.binding.mealCategoryImage)

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryList[position])
        }
    }
}