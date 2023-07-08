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

class MarvelAdapter(
    private var fnClick: (MarvelChars) -> Unit //no devuelve nada
) :
    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {
    var items: List<MarvelChars> = listOf()
    class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: MarvelCharactersBinding = MarvelCharactersBinding.bind(view)



            fun render(
        item: MarvelChars,
        fnClick: (MarvelChars) -> Unit
        ) {
            binding.txtNameMarvl.text = item.name;
            binding.txtComic.text = item.comic;
            Picasso.get().load(item.image).into(binding.imgMarvel)
            itemView.setOnClickListener{
                //Snackbar.make(binding.imgMarvel, item.name, Snackbar.LENGTH_SHORT).show()
                fnClick(item)
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelAdapter.MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MarvelViewHolder(
            inflater.inflate(
                R.layout.marvel_characters,
                parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        holder.render(items[position], fnClick)
    }

    override fun getItemCount(): Int = items.size

    fun updateListAdapter(newitems : List<MarvelChars>){
        this.items = this.items.plus(newitems)
        notifyDataSetChanged()
    }

    fun replaceListAdapter(newitems : List<MarvelChars>){
        this.items = newitems
        notifyDataSetChanged()
    }

}