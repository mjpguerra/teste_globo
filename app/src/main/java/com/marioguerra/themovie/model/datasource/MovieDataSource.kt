package com.marioguerra.themovie.model.datasource

import android.app.Application
import androidx.paging.PageKeyedDataSource
import com.marioguerra.themovie.R
import com.marioguerra.themovie.model.Movie
import com.marioguerra.themovie.model.MovieResponseDB
import com.marioguerra.themovie.service.MovieDataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MovieDataSource (
    val application: Application,
    private val movieDataService : MovieDataService
) : PageKeyedDataSource<Long, Movie>() {

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Movie>
    ) {
        movieDataService
            .getPopularMoviesWithPaging(
                application.applicationContext.getString(R.string.API_KEY),
                1
            )
            .enqueue(object : Callback<MovieResponseDB> {
                override fun onResponse(
                    call: Call<MovieResponseDB>,
                    response: Response<MovieResponseDB>
                ) {
                    response.body()?.movies?.let {
                        val movieList = it as ArrayList<Movie>
                        callback.onResult(movieList, null, 2)
                    }
                }

                override fun onFailure(call: Call<MovieResponseDB>, t: Throwable) {

                }
            })
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Movie>) {
        movieDataService
            .getPopularMoviesWithPaging(
                application.applicationContext.getString(R.string.API_KEY),
                params.key
            )
            .enqueue(object : Callback<MovieResponseDB> {
                override fun onResponse(
                    call: Call<MovieResponseDB>,
                    response: Response<MovieResponseDB>
                ) {
                    response.body()?.movies?.let {
                        val movieList = it as ArrayList<Movie>
                        callback.onResult(movieList, params.key + 1)
                    }
                }

                override fun onFailure(call: Call<MovieResponseDB>, t: Throwable) {

                }
            })
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Movie>) {

    }
}