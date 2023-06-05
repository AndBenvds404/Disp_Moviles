package com.example.pedidosonline.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosonline.databinding.ActivityMainBinding
import com.example.pedidosonline.databinding.ActivitySecondBinding
import com.google.android.material.snackbar.Snackbar

class SecondActivity : AppCompatActivity(){

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        /*binding.button.setOnClickListener{
            binding.textView.text="SAPA"
           //A donde me lleva el intent
            startActivity(intent)
        }*/

        initClass()
    }

    fun initClass(){


        /*Log.d("uce", "Entrando a start")  debug en la terminal*/



        binding.btCambiar.setOnClickListener{

            var intent= Intent(this, ActivityMainBinding ::class.java)


            startActivity(intent)

            Snackbar.make(
                binding.loginSegundo,"regresando",
                Snackbar.LENGTH_LONG).show()
        }


    }
}