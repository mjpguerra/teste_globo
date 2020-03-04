package com.marioguerra.themovie.di

import android.app.Application
import com.marioguerra.themovie.util.provider.AppSchedulerProvider
import com.marioguerra.themovie.view.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        MovieStoreRepositoryModule::class,
        MovieDataSourceFactoryModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}


