package com.example.myapplication2.logic.marvelLogic

import com.example.myapplication2.data.connections.ApiConnection
import com.example.myapplication2.data.endpoints.JikanEndpoint
import com.example.myapplication2.data.endpoints.MarvelEndPoint
import com.example.myapplication2.data.marvel.MarvelChars

class MarvelLogic {

    suspend fun getAllMarvel(name: String, limit:Int): List<MarvelChars> {


        var itemList = arrayListOf<MarvelChars>()
        val call = ApiConnection.getService(
            ApiConnection.typeApi.Marvel, MarvelEndPoint::class.java
        )

        if (call != null) {
            val response = call.getCharactersStartWith(name, limit)

            if (response.isSuccessful) {

                response.body()!!.data.results.forEach {
                    var comic: String = "no encontrado"
                    if (it.comics.items.size > 0) {
                        comic = it.comics.items[0].name
                    }
                    val m = MarvelChars(
                        it.id,
                        it.name,
                        comic,
                        it.thumbnail.path + "." +it.thumbnail.extension
                    )
                    itemList.add(m)
                }

            }else{

            }


        }



        return itemList
    }
}