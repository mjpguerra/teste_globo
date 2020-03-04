package com.marioguerra.themovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.marioguerra.themovie.model.ListResponse
import com.marioguerra.themovie.model.Movie
import com.marioguerra.themovie.model.datasource.MovieDataSource
import com.marioguerra.themovie.model.datasource.MovieDataSourceFactory
import com.marioguerra.themovie.model.repository.MovieStoreRepository
import com.marioguerra.themovie.util.state.Event
import com.marioguerra.themovie.util.state.SideEffect
import com.marioguerra.themovie.util.state.State
import com.marioguerra.themovie.util.state.StatefulViewModel
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val movieStoreRepository: MovieStoreRepository,
    factory: MovieDataSourceFactory
) :  StatefulViewModel<MainActivityViewModel.SearchState, MainActivityViewModel.SearchEvent>(SearchState.Empty) {

    private var movieDataSourceLiveData: MutableLiveData<MovieDataSource> = factory.getMutableLiveData()
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
    fun searchMovie(query: String, currentPage: Int = 1): LiveData<ListResponse<Movie>> = movieStoreRepository.searchMovie(query , currentPage)


    sealed class SearchState : State {
        object Empty : SearchState()
        object Loading : SearchState()
        data class DataLoaded(val keyword: String, val totalPages: Int, val page: Int, val data: List<Movie>) : SearchState()
        data class Error(val throwable: Throwable) : SearchState()
    }

    sealed class SearchEvent : Event {
        data class LoadingQueryFailure(val throwable: Throwable) : SearchEvent()
        data class LoadingQuerySuccess(val keyword: String, val totalPages: Int, val page: Int, val data: List<Movie>) : SearchEvent()

        sealed class Action {
            data class Query(val query: String) : SearchEvent()
            object LoadNewPage : SearchEvent()
            object Clear : SearchEvent()
        }
    }

    override val stateGraph = stateGraph {
        globalEvents {
            on<SearchEvent.Action.Clear> { transitionTo(SearchState.Empty, SideEffect.of { clearDisposables() }) }
        }

        state<SearchState.Empty> {
            on<SearchEvent.Action.Query> { transitionTo(SearchState.Loading, SideEffect.of { search(it.query) }) }
            on<SearchEvent.Action.LoadNewPage> { transitionTo(SearchState.Loading) }
        }
        state<SearchState.Loading> {
            on<SearchEvent.Action.Query> { dontTransition(SideEffect.of {
                clearDisposables()
                search(it.query)
            })}
            on<SearchEvent.Action.LoadNewPage> { dontTransition() }
            on<SearchEvent.LoadingQuerySuccess> { transitionTo(SearchState.DataLoaded(it.keyword, it.totalPages, it.page, it.data)) }
            on<SearchEvent.LoadingQueryFailure> { transitionTo(SearchState.Error(it.throwable)) }
        }
        state<SearchState.DataLoaded> {
            on<SearchEvent.Action.Query> { transitionTo(SearchState.Loading, SideEffect.of {
                clearDisposables()
                search(it.query)
            })}
            on<SearchEvent.Action.LoadNewPage> { transitionTo(SearchState.Loading, SideEffect.of { search(keyword, page + 1) }) }
        }
        state<SearchState.Error> {
            on<SearchEvent.Action.Query> { transitionTo(SearchState.Loading, SideEffect.of { search(it.query) }) }
        }
    }

    private fun search(query: String, currentPage: Int = 1) {

        if (currentPage == 1) {
            invokeAction(SearchEvent.Action.Clear)
            invokeAction(SearchEvent.Action.LoadNewPage)
        }

        searchMovie(query, currentPage).observeForever {
            it?.let { pagedList ->
                invokeAction(
                    when {
                        pagedList.results.isEmpty() -> SearchEvent.Action.Clear
                        else -> SearchEvent.LoadingQuerySuccess(
                            keyword = query,
                            totalPages = it.totalPages,
                            page = it.page,
                            data = it.results
                        )})
            }
        }
    }
}