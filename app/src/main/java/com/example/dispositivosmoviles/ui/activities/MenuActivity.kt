package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCamara.setOnClickListener{
            var intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
        binding.btnBiometric.setOnClickListener{
            var intent = Intent(this, BiometricActivity::class.java)
            startActivity(intent)
        }
        binding.btnNotification.setOnClickListener{
            var intent = Intent(this, NotificacionActivity::class.java)
            startActivity(intent)
        }
        binding.btnMarvel.setOnClickListener{
            var intent = Intent(this, PrincipalActivity::class.java)
            startActivity(intent)
        }
        binding.btnChatGPT.setOnClickListener{
            var intent = Intent(this, ChatGptActivity::class.java)
            startActivity(intent)
        }

        binding.btnSalir.setOnClickListener{
            var intent =Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}