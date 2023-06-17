package com.example.myapplication2.ui.utilities

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


class FragmentsManager {

    fun replaceFragment(manger: FragmentManager,
                        container: Int,
                        fragment:Fragment){
        with(manger.beginTransaction()){
            replace(container, fragment)
            addToBackStack(null)
            commit()
        }
    }

    fun addFragment(manger: FragmentManager,
                        container: Int,
                        fragment:Fragment){
        with(manger.beginTransaction()){
            add(container, fragment)
            commit()
        }
    }
}