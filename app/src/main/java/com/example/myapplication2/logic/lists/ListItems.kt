package com.example.myapplication2.logic.lists

import com.example.myapplication2.data.entities.LoginUser
import com.example.myapplication2.data.marvel.MarvelChars

class ListItems {

    fun returnItems(): List<LoginUser> {
        var items = listOf<LoginUser>(
            LoginUser("1", "1"),
            LoginUser("2", "2"),
            LoginUser("3", "3"),
            LoginUser("4", "4"),
            LoginUser("5", "5")
        )
        return items
    }

    fun returnMarvelChars(): List<MarvelChars>{
        val items = listOf(
            MarvelChars(
                1,
                "Colossus",
                "Gli Incredibili x-Men",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11130/111300304/5810917-image.jpeg"

            ),
            MarvelChars(
                2,
                "Jean Grey",
                "The uncanny X-Men",
                "https://comicvine.gamespot.com/a/uploads/scale_small/1/14487/8118044-b0c0e878-81c2-472e-ae1b-d1def184333b.jpeg"

            ),
            MarvelChars(
                3,
                "Scarlet Witch",
                "The Avengers",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8926321-large-3540780.jpg"

            ),
            MarvelChars(
                4,
                "Vision ",
                "Titans",
                "https://comicvine.gamespot.com/a/uploads/scale_small/1/10812/8925763-vision.jpg"

            ),
            MarvelChars(
                5,
                "Star-Lord",
                "Guardians of the Galaxy",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8820218-ezgif-1-9e302571b9.jpg"

            ),

        )
        return items
    }
}