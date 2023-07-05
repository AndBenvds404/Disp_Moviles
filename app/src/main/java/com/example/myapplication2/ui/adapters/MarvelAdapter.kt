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
    private var items: List<MarvelChars> = listOf(),
    private var fnClick:(MarvelChars)-> Unit):RecyclerView
                .Adapter<MarvelAdapter.MarvelViewHolder>() {
 //unit es cuando una funcion no retorna nada


    class MarvelViewHolder (view:View): RecyclerView.ViewHolder(view){

        private val binding: MarvelCharactersBinding = MarvelCharactersBinding.bind(view)

        fun render(item: MarvelChars,
                   fnClick:(MarvelChars)-> Unit){

            binding.imgMarvel.bringToFront()
            binding.txtNameMarvl.text = item.name
            binding.txtComic.text = item.comic
            Picasso.get().load(item.image).into(binding.imgMarvel)


           /* binding.imgMarvel.setOnClickListener{
                Snackbar.make(binding.imgMarvel,
                    item.name,
                    Snackbar.LENGTH_SHORT)
                    .show()
                        }*/
            itemView.setOnClickListener{
                fnClick(item)
            }

        }
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelAdapter.MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)


        return MarvelViewHolder(inflater.inflate(R.layout.marvel_characters, parent,
            false))
    }

    override fun onBindViewHolder(holder:
                                  MarvelViewHolder, position: Int) {
        holder.render(items[position],fnClick)
    }


    override fun getItemCount():Int = items.size

    fun updateListItems(newItems:List<MarvelChars>){
        items = this.items.plus(newItems)
        notifyDataSetChanged()
    }

    fun replaceItemsAdapter(newItems:List<MarvelChars>){
        items = newItems
        notifyDataSetChanged()
    }

}