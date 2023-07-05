package com.example.myapplication2.data.connections

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication2.data.dao.marvel.MarvelCharsDAO
import com.example.myapplication2.data.entities.marvel.characters.database.MarvelCharsDB

@Database(
    entities = [MarvelCharsDB::class],
    version = 1
)

abstract class MarvelConnectionDB: RoomDatabase() {

    abstract  fun marvelDao(): MarvelCharsDAO


}