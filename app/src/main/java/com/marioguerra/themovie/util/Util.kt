package com.marioguerra.themovie.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.marioguerra.themovie.R

const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
const val BASE_URL = "https://api.themoviedb.org/3/"

const val MOVIE_KEY_INTENT = "movie_key_intent"

fun ImageView.loadImage(url: String?) {
    val option = RequestOptions()
        .placeholder(R.drawable.loading)
        .error(R.drawable.ic_launcher_background)

    Glide.with(context)
        .setDefaultRequestOptions(option)
        .load(url)
        .into(this)
}

@BindingAdapter(("imageUrl"))
fun loadImageUrl(view: ImageView, url: String?) {
    view.loadImage("$IMAGE_URL$url")
}