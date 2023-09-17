package com.example.maihang.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.maihang.R
import com.example.maihang.databinding.ActivityMainBinding
import com.example.maihang.db.MealDatabase
import com.example.maihang.viewModel.HomeViewModel
import com.example.maihang.viewModel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val viewModel: HomeViewModel by lazy {
        val mealDatabase=MealDatabase.getInstance(this)
        val homeViewModelFactory=HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this,homeViewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Setup Navigation
        val bottomNavigation=binding.bottomNavigation
        val navController=Navigation.findNavController(this, R.id.hostFragment)
        NavigationUI.setupWithNavController(bottomNavigation,navController)

    }
}