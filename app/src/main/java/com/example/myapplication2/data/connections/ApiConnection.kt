package com.example.myapplication2.data.connections

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.annotation.SuppressLint


object ApiConnection {

    public enum class typeApi{
        Jikan, Marvel
    }


    private val API_JIKAN = "https://api.jikan.moe/v4/"
    private val API_MARVEL = "https://gateway.marvel.com/v1/public/"

    @SuppressLint("SuspiciousIndentation")
    private fun getConnection(base:String): Retrofit {

        var retrofit = Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

            return retrofit
    }




    fun <T,E:Enum<E>> getService(api: E, service: Class<T>):T{
        var BASE = ""
        when(api.name){
            typeApi.Jikan.name -> {
                    BASE = API_JIKAN
            }
            typeApi.Jikan.name -> {
                 BASE = API_MARVEL
            }
        }
                return getConnection(BASE). create(service)

        }




}