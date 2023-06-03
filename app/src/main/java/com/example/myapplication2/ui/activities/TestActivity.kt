package com.example.myapplication2.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication2.R
import com.example.myapplication2.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding
    //Main activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        binding=ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun onStart() {
        super.onStart()
        //Scope function
        //Si esta seguro de que esa pantalla recibe un valor
        var name :String = ""
        //Clase chueca

        intent.extras.let{name= it?.getString("var1")!!  }


        Log.d("uce","Hola ${name}")
        initClass()
    }
    fun initClass(){
        binding.button.setOnClickListener{
            var intent = Intent(
                this,
                MainActivity::class.java)

            startActivity(intent)
        }
    }
}