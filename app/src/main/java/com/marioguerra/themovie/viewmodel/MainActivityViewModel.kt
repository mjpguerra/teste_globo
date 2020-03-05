package com.marioguerra.themovie.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.marioguerra.themovie.model.ListResponse
import com.marioguerra.themovie.model.Movie
import com.marioguerra.themovie.model.MovieResponseDB
import com.marioguerra.themovie.model.Results
import com.marioguerra.themovie.model.datasource.MovieDataSource
import com.marioguerra.themovie.model.datasource.MovieDataSourceFactory
import com.marioguerra.themovie.model.repository.MovieStoreRepository
import com.marioguerra.themovie.util.state.Event
import com.marioguerra.themovie.util.state.SideEffect
import com.marioguerra.themovie.util.state.State
import com.marioguerra.themovie.util.state.StatefulViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val movieStoreRepository: MovieStoreRepository,
    factory: MovieDataSourceFactory
) : ViewModel() {

    private var executor: Executor
    private var moviePagedList: LiveData<PagedList<Movie>>

    init {

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(10)
            .setPageSize(20)
            .setPrefetchDistance(4)
            .build()

        executor = Executors.newFixedThreadPool(5)

        moviePagedList = LivePagedListBuilder(factory, config)
            .setFetchExecutor(executor)
            .build()
    }

    fun getAllMovies(): LiveData<List<Movie>> = movieStoreRepository.getMutableLiveData()
    fun getMoviePagedList(): LiveData<PagedList<Movie>> = moviePagedList

     fun getMovies(movie: String) : Observable<Results> {
        return movieStoreRepository.searchMovie(movie)
    }

}