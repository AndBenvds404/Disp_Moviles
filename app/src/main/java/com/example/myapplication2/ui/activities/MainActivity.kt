package com.example.myapplication2.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication2.databinding.ActivityMainBinding
import com.example.myapplication2.logic.validator.LoginValidator
import com.google.android.material.snackbar.Snackbar



class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
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
        Log.d("uce", "Entrando a start")
        Log.d("UCE", "saliendo de Test")


        binding.btIngresar .setOnClickListener{

            val loginVal = LoginValidator()
            val check = loginVal.checklogin(
                binding.inputEmail.text.toString(),
                binding.inputPassword .text.toString()
            )

            if(check) {
                var intent= Intent(this, TestActivity::class.java)

                intent.putExtra("var1","Login Correcto")

                startActivity(intent)

                /*Snackbar.make(
                    binding.txtBienvenida,"Correcto",
                    Snackbar.LENGTH_LONG).show()*/
            }else{
                Snackbar.make(
                    binding.txtBienvenida ,"usuario o contrasenia invalidos",
                    Snackbar.LENGTH_LONG).show()
            }



            //startActivity(intent)




        }
    }

}