package com.example.pedidosonline.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosonline.databinding.ActivityMainBinding
import com.example.pedidosonline.logic.validator.LoginValidator
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var bindindg: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindindg=ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindindg.root)
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

        bindindg.btIngresar.setOnClickListener{

            val loginVal = LoginValidator()
            val check = loginVal.checklogin(
                bindindg.inputEmail.text.toString(),
                bindindg.inputPassword.text.toString()
                )

            if(check) {
                var intent= Intent (this, ActivityMainBinding ::class.java)


                startActivity(intent)

                Snackbar.make(
                    bindindg.txtBienvenida ,"Correcto",
                    Snackbar.LENGTH_LONG).show()
            }else{
                Snackbar.make(
                    bindindg.txtCorreo ,"usuario o contrasenia invalidos",
                    Snackbar.LENGTH_LONG).show()

            }



        }
    }



}