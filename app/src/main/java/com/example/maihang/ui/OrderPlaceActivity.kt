package com.example.maihang.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.voice.VoiceInteractionSession.VisibleActivityCallback
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.maihang.R
import com.example.maihang.databinding.ActivityOrderPlaceBinding
import com.example.maihang.viewModel.MealActivityViewModel
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import java.lang.Exception
import kotlin.math.roundToInt

class OrderPlaceActivity : AppCompatActivity(),PaymentResultListener{
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

        binding.btnPay.setOnClickListener {
           val amt=binding.totalAmount.text.toString()
            val amount= (amt.toFloat() * 100).roundToInt().toInt()
           
            makePayment(amount)
        }
    }

    private fun makePayment(amount: Int) {
       val co=Checkout()
        try {
             val options=JSONObject()
            options.put("name","Maihang:The Food Zone")
            options.put("description","Pay")
            options.put("currency","INR")
            options.put("amount",amount)

            co.open(this,options)
        }catch (e:Exception){
            Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
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

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
    }
}