package com.example.dispositivosmoviles.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.logic.data.MarvelChars
import com.example.dispositivosmoviles.databinding.FragmentFirstBinding
import com.example.dispositivosmoviles.logic.data.getMarvelCharsDB
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelLogic
import com.example.dispositivosmoviles.ui.activities.DetailsMarvelItem
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapter
import com.example.dispositivosmoviles.ui.utilities.DispositivosMoviles
import com.example.dispositivosmoviles.ui.utilities.Metodos
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.RuntimeException
import kotlin.collections.mutableListOf


class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding;

    private var rvAdapter: MarvelAdapter = MarvelAdapter( { sendMarvelItem(it)}, {saveMarveItem(it) })
    private lateinit var lManager: LinearLayoutManager
    private lateinit var gManager: GridLayoutManager
    private val limit = 99
    private var offset = 0

    private var marvelCharsItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentFirstBinding.inflate(
            layoutInflater, container, false
        )


        lManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )

        //Dos por fila
        gManager = GridLayoutManager(requireActivity(), 2)
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
        chargeDataRVAPI(limit, offset)

        binding.rvSwipe.setOnRefreshListener {
            chargeDataRVInit(limit, offset)
            binding.rvSwipe.isRefreshing = false
            lManager.scrollToPositionWithOffset(5, 20)
        }

        binding.rvMarvelChars.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dx > 0) {
                        val v = lManager.childCount//Cuantos han pasado
                        val p = lManager.findFirstVisibleItemPosition()//Cual es mi posicion actual
                        val t = lManager.itemCount//Cuantos tengo en total

                        if ((v + p) >= t) {
                            lifecycleScope.launch((Dispatchers.Main))
                            {
                                val items = with(Dispatchers.IO) {


                                    MarvelLogic().getAllMarvelChars(offset, limit)
                                    //JikanAnimeLogic().getAllAnimes()
                                }
                                rvAdapter.updateListAdapter((items))
                                this@FirstFragment.offset+= offset

                            }
                        }

                    }

                }
            })

        binding.txtFilter.addTextChangedListener { filteredText ->
            val newItems = marvelCharsItems.filter { items ->
                items.name.lowercase().contains(filteredText.toString().lowercase())
            }

            rvAdapter.replaceListAdapter(newItems)
        }

    }

    fun corrotine() {
        lifecycleScope.launch(Dispatchers.Main) {
            var name = "Bayron"

            name = withContext(Dispatchers.IO)
            {
                name = "Jairo"
                return@withContext name
            }

            binding.cardView.radius
        }
    }

    fun sendMarvelItem(item: MarvelChars) {
        //Intent(contexto de la activity, .class de la activity)
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("item", item)//mandamos los items a la otra activity
        startActivity(i)
    }


    fun saveMarveItem(item:MarvelChars):Boolean{

        lifecycleScope.launch(Dispatchers.Main){
        Log.d("","Guardando")
            try {
                DispositivosMoviles.getDbInstance().marvelDao().insertMarvelCharacter(
                    listOf(item.getMarvelCharsDB())

                )
            } catch (e: Exception) {
                throw RuntimeException(e.message)
            }

        }
        return true
    }

    fun chargeDataRVAPI(limit: Int, offset:Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            //rvAdapter.items = JikanAnimeLogic().getAllAnimes()
            marvelCharsItems = withContext(Dispatchers.IO) {
                return@withContext (MarvelLogic().getAllMarvelChars(
                    offset, limit
                ))
            }
            rvAdapter.items = marvelCharsItems

            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter;
                this.layoutManager = gManager;

            }
            this@FirstFragment.offset = offset+ limit;
        }

    }



    fun chargeDataRVInit(limit: Int, offset: Int) {

        if(Metodos().isOnline(requireActivity())){

            lifecycleScope.launch(Dispatchers.Main) {
                //rvAdapter.items = JikanAnimeLogic().getAllAnimes()
                marvelCharsItems = withContext(Dispatchers.IO){
                    return@withContext MarvelLogic().getInitChars(limit, offset)
                }
                this@FirstFragment.offset+=limit

                rvAdapter.items =marvelCharsItems
                binding.rvMarvelChars.apply {

                    this.adapter =rvAdapter;
                    this.layoutManager= gManager

                }
            }
        }else{
            Snackbar.make(binding.cardView, "no hay conexion",
                Snackbar.LENGTH_LONG
                ).show()
        }



        }



    }
