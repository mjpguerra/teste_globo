package com.marioguerra.themovie.di

import androidx.annotation.NonNull
import com.marioguerra.themovie.service.MovieDataService
import com.marioguerra.themovie.util.BASE_URL
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
@Module
class NetworkModule {

  @Provides
  @Singleton
  fun provideHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(@NonNull okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl(
        BASE_URL)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  @Provides
  @Singleton
  fun provideMovieDataService(@NonNull retrofit: Retrofit): MovieDataService {
    return retrofit.create(MovieDataService::class.java)
  }

}
