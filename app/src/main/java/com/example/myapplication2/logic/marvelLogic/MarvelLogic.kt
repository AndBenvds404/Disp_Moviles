package com.example.myapplication2.logic.marvelLogic

import android.util.Log
import com.example.myapplication2.data.connections.ApiConnection
import com.example.myapplication2.data.endpoints.JikanEndpoint
import com.example.myapplication2.data.endpoints.MarvelEndPoint
import com.example.myapplication2.data.entities.marvel.getMarvelChars
import com.example.myapplication2.data.marvel.MarvelChars

class MarvelLogic {

    suspend fun getMarvelChars(name: String, limit:Int): ArrayList <MarvelChars> {


        var itemList = arrayListOf<MarvelChars>()
        var response = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndPoint::class.java
        ).getCharactersStartWith(name, limit)
        if (response.isSuccessful) {
            response.body()!!.data.results.forEach {
        val m = it.getMarvelChars()

                itemList.add(m)
            }
        } else {
            Log.d("UCE", response.toString())
        }
        return itemList
    }

    suspend fun getAllMarvelChars(offset: Int, limit:Int): ArrayList <MarvelChars> {


        var itemList = arrayListOf<MarvelChars>()
        var response = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndPoint::class.java
        ).getAllMarvelCharacters(offset, limit)
        if (response.isSuccessful) {
            response.body()!!.data.results.forEach {
                val m = it.getMarvelChars()

                itemList.add(m)
            }
        } else {
            Log.d("UCE", response.toString())
        }
        return itemList
    }
}