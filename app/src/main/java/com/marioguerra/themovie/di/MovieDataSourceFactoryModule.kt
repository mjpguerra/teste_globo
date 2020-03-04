package com.marioguerra.themovie.di

import android.app.Application
import com.marioguerra.themovie.model.datasource.MovieDataSourceFactory
import com.marioguerra.themovie.service.MovieDataService
import dagger.Module
import dagger.Provides

@Module
class MovieDataSourceFactoryModule {

    @Provides
    fun provideMovieDataSourceFactory(application: Application, movieDataService : MovieDataService) =
        MovieDataSourceFactory(application, movieDataService)
}