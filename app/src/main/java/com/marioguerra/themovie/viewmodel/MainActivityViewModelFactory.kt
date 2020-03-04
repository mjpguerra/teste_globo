package com.marioguerra.themovie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marioguerra.themovie.model.datasource.MovieDataSourceFactory
import com.marioguerra.themovie.model.repository.MovieStoreRepository
import javax.inject.Inject

class MainActivityViewModelFactory 
@Inject constructor(
    private val movieDataSourceFactory: MovieDataSourceFactory,
    private val movieStoreRepository: MovieStoreRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MainActivityViewModel(movieStoreRepository, movieDataSourceFactory) as T
}