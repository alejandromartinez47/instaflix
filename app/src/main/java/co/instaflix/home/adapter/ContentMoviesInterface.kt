package co.instaflix.home.adapter

import co.instaflix.home.model.Movies
import co.instaflix.home.model.Series

interface ContentMoviesInterface {
    fun onItemSelect(movies: Movies)
}