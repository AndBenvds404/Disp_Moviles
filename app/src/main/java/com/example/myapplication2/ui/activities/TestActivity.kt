package com.example.myapplication2.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import androidx.fragment.app.Fragment
import com.example.myapplication2.ui.fragments.NewFragment
import com.example.myapplication2.R
import com.example.myapplication2.databinding.ActivityTestBinding
import com.example.myapplication2.ui.utilities.FragmentsManager
import com.google.android.material.snackbar.Snackbar

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_test)
        binding=ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun onStart() {
        super.onStart()
        FragmentsManager().replaceFragment(supportFragmentManager,
            binding.fragment1.id, NewFragment()
        )

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.men -> {

                    FragmentsManager().replaceFragment(supportFragmentManager,
                    binding.fragment1.id, NewFragment()
                    )
                    true
                }
                R.id.mayorQue -> {

                    true
                }
                R.id.woman -> {

                    true
                }
                else -> false
            }
        }


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