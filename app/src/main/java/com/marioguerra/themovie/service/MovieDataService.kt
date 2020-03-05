package com.marioguerra.themovie.service

import com.marioguerra.themovie.model.ListResponse
import com.marioguerra.themovie.model.Movie
import com.marioguerra.themovie.model.MovieResponseDB
import com.marioguerra.themovie.model.Results
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDataService {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): Call<MovieResponseDB>

    @GET("movie/popular")
    fun getPopularMoviesWithPaging(
        @Query("api_key") apiKey: String,
        @Query("page") page: Long
    ): Call<MovieResponseDB>

    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") apiKey: String ,
        @Query("query") nameMovie: String
    ): Observable<Results>


}
