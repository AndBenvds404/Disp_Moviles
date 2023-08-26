package com.example.dispositivosmoviles.ui.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityCameraBinding
import com.example.dispositivosmoviles.databinding.ActivityResultBinding

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCapture.setOnClickListener{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraResul.launch(intent)
        }

        binding.imageCapture.setOnClickListener{
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_TEXT, "hola prro compalte")
            shareIntent.setType("text/plain")

            startActivity(Intent.createChooser(shareIntent, "compartir"))
        }

        binding.btnBack.setOnClickListener{
            var backIntent = Intent(this, MenuActivity::class.java)
            startActivity(backIntent)

        }
    }


    private val cameraResul =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
            when (result.resultCode){
                Activity.RESULT_OK ->{
                    val image = result.data?.extras?.get("data") as Bitmap
                    binding.imageCapture.setImageBitmap(image)
                }
                Activity.RESULT_CANCELED ->{}
            }


    }
}