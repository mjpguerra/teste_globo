package com.marioguerra.themovie.model.datasource

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.marioguerra.themovie.model.Movie
import com.marioguerra.themovie.service.MovieDataService
import javax.inject.Inject


class MovieDataSourceFactory @Inject constructor(
    val application: Application, val movieDataService : MovieDataService
) : DataSource.Factory<Long, Movie>() {
    private lateinit var movieDataSource: MovieDataSource

    private var mutableLiveData: MutableLiveData<MovieDataSource> = MutableLiveData()

    override fun create(): DataSource<Long, Movie> {
        movieDataSource = MovieDataSource(application, movieDataService)
        mutableLiveData.postValue(movieDataSource)
        return movieDataSource
    }

    fun getMutableLiveData(): MutableLiveData<MovieDataSource> = mutableLiveData
}