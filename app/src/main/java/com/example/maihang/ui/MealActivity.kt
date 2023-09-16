package com.example.maihang.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.maihang.R
import com.example.maihang.databinding.ActivityMealBinding
import com.example.maihang.fragments.HomeFragment
import com.example.maihang.viewModel.MealActivityViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealImage:String
    private lateinit var mealMvvm:MealActivityViewModel
    private lateinit var youtubeLink:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.collapsingToolBar.setExpandedTitleColor(ContextCompat.getColor(this,R.color.meal))
        binding.collapsingToolBar.setExpandedTitleTextAppearance(R.style.ExpandedTitleText)

        mealMvvm=ViewModelProvider(this)[MealActivityViewModel::class.java]
        loadCase()
        getInformationFromIntent()
        setInformationInView()

        mealMvvm.getMealById(mealId)
        observeLiveMealData()

        onYoutubeImageClick()

        binding.btnBuy.setOnClickListener {
            val intent=Intent(this,OrderPlaceActivity::class.java)
            intent.putExtra("ID",mealId)
            intent.putExtra("NAME",mealName)
            intent.putExtra("IMAGE",mealImage)
            startActivity(intent)
        }
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observeLiveMealData() {
        mealMvvm.observeMealDetails().observe(this, Observer {
            responseCase()
            binding.mealCat.text="Category: ${it.strCategory}"
            binding.txtArea.text="Area: ${it.strArea}"
            binding.mealDescription.text=it.strInstructions
            youtubeLink=it.strYoutube.toString()
        })
    }

    private fun setInformationInView() {
        Glide.with(applicationContext)
            .load(mealImage)
            .into(binding.mealDetailsImage)
        binding.collapsingToolBar.title=mealName
    }

    private fun getInformationFromIntent() {
        val intent=intent
        mealId=intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName=intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealImage=intent.getStringExtra(HomeFragment.MEAL_IMAGE)!!
    }

    private fun loadCase(){
        binding.lineProgressBar.visibility=View.VISIBLE
        binding.mealCat.visibility=View.INVISIBLE
        binding.mealDescription.visibility=View.INVISIBLE
        binding.btnFloat.visibility=View.INVISIBLE
        binding.txtArea.visibility=View.INVISIBLE
    }

    private fun responseCase(){
        binding.lineProgressBar.visibility=View.INVISIBLE
        binding.mealCat.visibility=View.VISIBLE
        binding.mealDescription.visibility=View.VISIBLE
        binding.btnFloat.visibility=View.VISIBLE
        binding.txtArea.visibility=View.VISIBLE
    }
}