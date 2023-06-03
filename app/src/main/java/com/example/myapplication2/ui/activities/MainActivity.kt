package com.example.myapplication2.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication2.databinding.ActivityMainBinding
import com.example.myapplication2.logic.validator.LoginValidator
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
@SuppressLint("MissingInflateId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
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
        binding.button4.setOnClickListener{

            val loginVal = LoginValidator()
            val check = loginVal.checklogin(
                binding.textView2.text.toString(),
                binding.textView3.text.toString()
            )

            if(check) {
                var intent= Intent(this, MainActivity::class.java)

                intent.putExtra("var1","Login Correcto")

                startActivity(intent)
                Snackbar.make(
                    binding.textView2,"Correcto",
                    Snackbar.LENGTH_LONG).show()
            }


           /* var intent = Intent(
                this,
                TestActivity::class.java)

            intent.putExtra("var1","Sol")
            intent.putExtra("var2",binding.editTextText2.toString())

            startActivity(intent)

            Snackbar.make(
                binding.textView2,"usuario o contrasenia invalidos",
                Snackbar.LENGTH_LONG).show()*/


        }
    }

}