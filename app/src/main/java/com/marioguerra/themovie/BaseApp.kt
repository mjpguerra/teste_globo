package com.marioguerra.themovie

import com.marioguerra.themovie.di.DaggerAppComponent
import dagger.android.DaggerApplication

@Suppress("unused")
class BaseApp : DaggerApplication() {

    private val appComponent = DaggerAppComponent.factory().create(this)

    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector() = appComponent
}