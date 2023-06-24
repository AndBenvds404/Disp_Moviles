package com.example.myapplication2.logic.jikanLogic

import com.example.myapplication2.data.connections.ApiConnection
import com.example.myapplication2.data.endpoints.JikanEndpoint
import com.example.myapplication2.data.marvel.MarvelChars

class JikanAnimeLogic {
    suspend fun  getAllAnimes(): List<MarvelChars>{

        var call = ApiConnection.getJcanConnection()
        val response = call.create(JikanEndpoint::class.java).getAllAnimes()
        var itemList = arrayListOf<MarvelChars>()

        if (response.isSuccessful){
            response.body()!!.data.forEach{
               val m=  MarvelChars(
                   it.mal_id
                ,it.title
                   ,it.titles[0].title
                ,it.images.jpg.image_url
               )
                itemList.add(m)
            }

        }
        return itemList
    }
}