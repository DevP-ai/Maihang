package com.example.maihang.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.voice.VoiceInteractionSession.VisibleActivityCallback
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.maihang.R
import com.example.maihang.databinding.ActivityOrderPlaceBinding
import com.example.maihang.viewModel.MealActivityViewModel

class OrderPlaceActivity : AppCompatActivity() {
    private lateinit var mealMvvm: MealActivityViewModel
    private lateinit var binding:ActivityOrderPlaceBinding
    private lateinit var id:String
    private lateinit var name:String
    private lateinit var image:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOrderPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.collapsingToolBar.setExpandedTitleColor(ContextCompat.getColor(this,R.color.meal))
        binding.collapsingToolBar.setExpandedTitleTextAppearance(R.style.ExpandedTitleText)


        mealMvvm= ViewModelProvider(this)[MealActivityViewModel::class.java]

        getInfoFromIntent()
        setInfo()





    }

    private fun setInfo() {
        Glide.with(this)
            .load(image)
            .into(binding.imgProduct)
        binding.collapsingToolBar.title=name

        binding.productName.text=name
        binding.productId.text=id
    }

    private fun getInfoFromIntent() {
        val intent=intent
        id=intent.extras!!.getString("ID")!!
        name=intent.extras!!.getString("NAME")!!
        image=intent.extras!!.getString("IMAGE")!!
    }


    private fun loadCase(){
        binding.lineProgressBar.visibility= View.VISIBLE
    }

    private fun unloadCase(){
        binding.lineProgressBar.visibility=View.GONE
    }
}