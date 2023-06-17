package com.example.myapplication2.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication2.R
import com.example.myapplication2.databinding.FragmentNewBinding
import com.example.myapplication2.logic.lists.ListItems
import com.example.myapplication2.ui.adapters.MarvelAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [NewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewFragment : Fragment() {

    private lateinit var binding: FragmentNewBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentNewBinding.inflate(  layoutInflater, container,
            false)
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

        val rvAdapter = MarvelAdapter(ListItems().returnMarvelChars())
        val rvMarvel = binding.rvMarvelChars

        rvMarvel.adapter=rvAdapter
        rvMarvel.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )

    }



}