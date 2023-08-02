package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityProgressBinding
import com.example.dispositivosmoviles.ui.viewmodels.ProgressViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgressBinding
    private val progressViewModel by viewModels<ProgressViewModel>();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //LIVEDATA
        //cada vez que exista un cambio se va ejecutar el progressState
        progressViewModel.progressState.observe(this, Observer{
            binding.progressBar.visibility = it
        })

        progressViewModel.items.observe(this, Observer {
            Toast.makeText(this,it[0].name ,Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, NotificacionActivity::class.java))
        })

        //LISTENERS
        binding.btnProgreso.setOnClickListener{
            progressViewModel.proccesBackground(3000)
        }

        binding.btnProgreso1.setOnClickListener{
            lifecycleScope.launch (Dispatchers.IO){
                progressViewModel.getMarvelChars(0,80)
            }


        }


    }
}