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
import androidx.recyclerview.widget.GridLayoutManager
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
    //private lateinit var binding: FragmentFirstBinding;
    private var rvAdapter: MarvelAdapter = MarvelAdapter{sendMarvelItem(it)}
    private lateinit var lmanager : LinearLayoutManager
    private lateinit var gManager: GridLayoutManager
    private var page = 1

    private var marvelCharsItems : MutableList<MarvelChars> = mutableListOf<MarvelChars>()


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            binding = FragmentNewBinding.inflate(
                layoutInflater, container, false
            )


            lmanager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false)
        gManager= GridLayoutManager(requireActivity(),2)
            return binding.root

        }



            override fun onStart() {
                super.onStart();
                val names = arrayListOf<String>("A", "B", "C", "D", "E")

                val adapter1 = ArrayAdapter<String>(
                    requireActivity(),
                    android.R.layout.simple_spinner_item,
                    names
                )

                binding.spinner.adapter = adapter1
                chargeDataRV(5)

                binding.rvSwipe.setOnRefreshListener {
                    chargeDataRV(5)
                    binding.rvSwipe.isRefreshing = false
                    lmanager.scrollToPositionWithOffset(5, 20)
                }

                binding.rvMarvelChars.addOnScrollListener(
                    object: RecyclerView.OnScrollListener(){
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            if(dx>0){
                                val v = lmanager.childCount//Cuantos han pasado
                                val p = lmanager.findFirstVisibleItemPosition()//Cual es mi posicion actual
                                val t = lmanager.itemCount//Cuantos tengo en total

                                if ((v + p) >= t) {
                                    lifecycleScope.launch((Dispatchers.Main))
                                    {
                                        val x = with(Dispatchers.IO){
                                            MarvelLogic().getAllMarvelChars(0, 99 )
                                            //JikanAnimeLogic().getAllAnimes()
                                        }
                                        rvAdapter.updateListAdapter((x))

                                    }
                                }

                            }

                        }
                    })

                binding.txtFilter.addTextChangedListener{ filteredText ->
                    val newItems = marvelCharsItems.filter {items ->
                        items.name.lowercase().contains(filteredText.toString().lowercase())
                    }

                    rvAdapter.replaceListAdapter(newItems)
                }

            }

            fun corrotine(){
                lifecycleScope.launch(Dispatchers.Main){
                    var name = "Bayron"

                    name = withContext(Dispatchers.IO)
                    {
                        name ="Jairo"
                        return@withContext name
                    }

                    binding.cardView.radius
                }
            }

            fun sendMarvelItem(item: MarvelChars) {
                //Intent(contexto de la activity, .class de la activity)
                val i = Intent(requireActivity(), DetailsMarverItems::class.java)
                i.putExtra("item", item)//mandamos los items a la otra activity
                startActivity(i)
            }

            fun chargeDataRV(pos:Int) {
                lifecycleScope.launch(Dispatchers.Main) {
                    //rvAdapter.items = JikanAnimeLogic().getAllAnimes()
                    marvelCharsItems = withContext(Dispatchers.IO){
                        return@withContext (MarvelLogic().getAllMarvelChars(
                            0   , 99
                        ))
                    }


                    rvAdapter.items = marvelCharsItems

                    binding.rvMarvelChars.apply {
                        this.adapter = rvAdapter;
                        this.layoutManager = lmanager;


                        lmanager.scrollToPositionWithOffset(pos ,10)
                    }
                }
                page++
            }


        }