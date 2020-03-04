package com.marioguerra.themovie.di

import android.app.Application
import com.marioguerra.themovie.model.repository.MovieStoreRepository
import com.marioguerra.themovie.service.MovieDataService
import com.marioguerra.themovie.util.provider.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class MovieStoreRepositoryModule {

    @Provides
    fun provideMovieRepository(application: Application, movieDataService : MovieDataService) =
        MovieStoreRepository(application, movieDataService)
}