package com.marioguerra.themovie.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marioguerra.themovie.R
import com.marioguerra.themovie.databinding.ActivityMovieBinding
import com.marioguerra.themovie.util.MOVIE_KEY_INTENT
import com.marioguerra.themovie.util.databinding.contentView
import kotlinx.android.synthetic.main.activity_movie.*

class MovieActivity : AppCompatActivity() {

    private val binding by contentView<MovieActivity, ActivityMovieBinding>(R.layout.activity_movie)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)

        intent?.let {
            if (it.hasExtra(MOVIE_KEY_INTENT)) {
                binding.movie = it.getParcelableExtra(MOVIE_KEY_INTENT)
            }
        }
    }
}
