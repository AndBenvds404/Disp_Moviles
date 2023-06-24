package com.example.myapplication2.data.endpoints

import com.example.myapplication2.data.entities.jikan.Aired
import com.example.myapplication2.data.entities.jikan.JikanAnimeEntity
import retrofit2.Response

import retrofit2.http.GET


interface JikanEndpoint {
    @GET("top/anime")
    suspend fun getAllAnimes(): Response <JikanAnimeEntity>

}