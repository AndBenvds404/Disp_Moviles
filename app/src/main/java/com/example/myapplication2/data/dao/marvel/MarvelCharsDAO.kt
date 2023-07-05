package com.example.myapplication2.data.dao.marvel

import androidx.room.Dao
import androidx.room.DeleteColumn
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication2.data.entities.marvel.characters.database.MarvelCharsDB
import com.example.myapplication2.data.marvel.MarvelChars


@Dao
interface MarvelCharsDAO {

    @Query("select * from MarvelCharsDB")
    fun getAllCharacters():List<MarvelCharsDB>

    @Query("select * from MarvelCharsDB where id=:pk")
    fun getOneCharacter(pk: Int): MarvelCharsDB

    @Insert
    fun insertMarvelChar(ch : List< MarvelCharsDB>)



}