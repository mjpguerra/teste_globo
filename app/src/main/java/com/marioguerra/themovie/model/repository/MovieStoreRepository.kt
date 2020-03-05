package com.marioguerra.themovie.model.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.marioguerra.themovie.R
import com.marioguerra.themovie.model.ListResponse
import com.marioguerra.themovie.model.Movie
import com.marioguerra.themovie.model.MovieResponseDB
import com.marioguerra.themovie.model.Results
import com.marioguerra.themovie.service.MovieDataService
import com.marioguerra.themovie.util.extension.schedule
import com.marioguerra.themovie.util.provider.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MovieStoreRepository @Inject constructor(val application: Application, val movieDataService : MovieDataService) {

    private var movieList: ArrayList<Movie> = arrayListOf()
    private var searchMovieLiveData: MutableLiveData<ListResponse<Movie>> = MutableLiveData()
    private var mutableLiveData: MutableLiveData<List<Movie>> = MutableLiveData()

    fun searchMovie(phrase: String): Observable<Results> {
        return movieDataService.searchMovie(application.applicationContext.getString(R.string.API_KEY), phrase)
    }

    fun getMutableLiveData(): MutableLiveData<List<Movie>> {
        movieDataService
            .getPopularMovies(application.applicationContext.getString(R.string.API_KEY))
            .enqueue(object : Callback<MovieResponseDB> {
                override fun onResponse(
                    call: Call<MovieResponseDB>,
                    response: Response<MovieResponseDB>
                ) {
                    response.body()?.movies?.let {
                        movieList = it as ArrayList<Movie>
                        mutableLiveData.value = movieList
                    }
                }

                override fun onFailure(call: Call<MovieResponseDB>, t: Throwable) {

                }
            })

        return mutableLiveData
    }
}