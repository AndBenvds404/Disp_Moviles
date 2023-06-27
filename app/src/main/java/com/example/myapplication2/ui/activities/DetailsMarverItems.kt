package com.example.myapplication2.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication2.R
import com.example.myapplication2.data.marvel.MarvelChars
import com.example.myapplication2.databinding.ActivityDetailsMarverItemsBinding
import com.example.myapplication2.databinding.MarvelCharactersBinding
import com.squareup.picasso.Picasso

class DetailsMarverItems : AppCompatActivity() {

    private  lateinit var binding: ActivityDetailsMarverItemsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMarverItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart(){
        super.onStart()

        /*var name : String? = ""
        intent.extras?.let {
             name = it.getString ("name")
        }

        if (!name.isNullOrEmpty()){
            binding.txtName.text = name

        }*/

        val itemOb = intent.getParcelableExtra<MarvelChars>("name")
        if(itemOb !=null){
            binding.txtName.text  = itemOb.name
            binding.txtNameComic.text = itemOb.name
            Picasso.get().load(itemOb.image).into(binding.imgMarvDetails)
        }

    }
}
