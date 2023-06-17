package com.example.myapplication2.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.R
import com.example.myapplication2.data.marvel.MarvelChars
import com.example.myapplication2.databinding.MarvelCharactersBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class MarvelAdapter(private val item: List<MarvelChars>):RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {



    class MarvelViewHolder (view:View): RecyclerView.ViewHolder(view){

        private val binding: MarvelCharactersBinding = MarvelCharactersBinding.bind(view)

        fun render(item: MarvelChars){
            println("Recibiendp a ${item.name}")
            binding.imgMarvel.bringToFront()
            binding.txtNameHero.text = item.name
            binding.txtComic.text = item.comic
            Picasso.get().load(item.image).into(binding.imgMarvel)

            binding.imgMarvel.setOnClickListener{
                Snackbar.make(binding.imgMarvel,
                    item.name,
                    Snackbar.LENGTH_SHORT)
                    .show()


            }

        }
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelAdapter.MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)


        return MarvelViewHolder(inflater.inflate(R.layout.marvel_characters, parent, false))
    }

    override fun onBindViewHolder(holder: MarvelViewHolder, position: Int) {
        holder.render(item[position])
    }


    override fun getItemCount():Int = item.size

}