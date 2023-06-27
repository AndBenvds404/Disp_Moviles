package com.example.myapplication2.data.entities.marvel

data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)