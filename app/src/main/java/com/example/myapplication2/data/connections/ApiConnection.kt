package com.example.myapplication2.data.connections

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConnection {

    fun getJcanConnection(): Retrofit {

    var retrofit = Retrofit.Builder()
    .baseUrl("https://api.jikan.moe/v4/")
        .addConverterFactory(GsonConverterFactory.create())
    .build();

        return retrofit

    }


}