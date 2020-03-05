package com.marioguerra.themovie.model

import com.google.gson.annotations.SerializedName

data class MovieResponseDB(
    val page: Int,
    @SerializedName("results")
    val movies: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)

data class Results(
    @SerializedName("results") val movies: List<Movie>
)