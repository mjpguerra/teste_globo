package com.marioguerra.themovie.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidAppModule(val application: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application
}