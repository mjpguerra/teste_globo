package com.marioguerra.themovie.di

import com.marioguerra.themovie.view.activity.MainActivity
import com.marioguerra.themovie.view.fragment.MovieFragment
import com.marioguerra.themovie.view.fragment.SearchFragment
import com.skydoves.themovies.di.annotations.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

  @ActivityScope
  @ContributesAndroidInjector
  internal abstract fun contributeMainActivity(): MovieFragment

  @ActivityScope
  @ContributesAndroidInjector
  internal abstract fun contributeSearchFragment(): SearchFragment

  @ActivityScope
  @ContributesAndroidInjector
  internal abstract fun contributeDashboardActivity(): MainActivity


}
