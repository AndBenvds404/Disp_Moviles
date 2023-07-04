package com.example.myapplication2.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.R
import com.example.myapplication2.data.marvel.MarvelChars
import com.example.myapplication2.databinding.FragmentNewBinding
import com.example.myapplication2.logic.jikanLogic.JikanAnimeLogic
import com.example.myapplication2.logic.lists.ListItems
import com.example.myapplication2.logic.marvelLogic.MarvelLogic
import com.example.myapplication2.ui.activities.DetailsMarverItems
import com.example.myapplication2.ui.activities.MainActivity
import com.example.myapplication2.ui.adapters.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [NewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewFragment : Fragment() {

    private lateinit var binding: FragmentNewBinding
    private lateinit var lmanager: LinearLayoutManager
    private var page = 1
    private lateinit var marvelCharchItems : MutableList<MarvelChars>
    private var  rvAdapter: MarvelAdapter = MarvelAdapter {
        sendMarvelItem(it)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentNewBinding.inflate(  layoutInflater, container,
            false)

        lmanager= LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )

        return binding.root
    }

    override fun onStart(){
        super.onStart()
        val names = arrayListOf<String>(
            "Andres", "Fabio", "Mario","Bob","Penelope"
        )
        val adapter = ArrayAdapter<String>(requireActivity() ,
            R.layout.simple_spinner,
            names)

        binding.spinner .adapter = adapter
        //binding.listwiew1.adapter = adapter
        chargeDaraRv("cap")

        binding.rvSwipe.setOnRefreshListener {
            chargeDaraRv("cap")
            binding.rvSwipe.isRefreshing=false
        }

        binding.rvMarvelChars.addOnScrollListener(
            object :RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if(dy>0){
                        val v = lmanager.childCount
                        val p = lmanager.findFirstVisibleItemPosition()
                        val t = lmanager.itemCount

                        if((v+p)>=t){
                            lifecycleScope.launch(Dispatchers.IO){
                                // val newItems = MarvelLogic().getAllMarvel(name = "spider", limit = 10)
                                val newItems = JikanAnimeLogic().getAllAnimes()
                                withContext(Dispatchers.Main){
                                    rvAdapter.updateListItems(newItems)
                                }


                            }
                        }
                    }


                }
        })

        binding.txtFilter.addTextChangedListener{filterText->

            val newItems = marvelCharchItems.filter { items->
                items.name.contains(filterText.toString())
            }

            rvAdapter.replaceItemsAdapter(newItems)
        }

    }
    fun sendMarvelItem(item: MarvelChars){
        val i = Intent(requireActivity(), DetailsMarverItems::class.java)
        i.putExtra("name", item)
        startActivity(i);
    }



    fun chargeDaraRv(search:String) {

        lifecycleScope.launch(Dispatchers.IO){
            var marvelCharchItems = MarvelLogic().getAllMarvel(
                "spider", page*2
            )
        rvAdapter.items=JikanAnimeLogic().getAllAnimes()
        // val newItems = MarvelLogic().getAllMarvel(name = search, limit = 20)


            withContext(Dispatchers.Main){

                with(binding.rvMarvelChars){

                    this.adapter = rvAdapter
                    this.layoutManager = lmanager
            }
            }
            page++
        }
    }

}