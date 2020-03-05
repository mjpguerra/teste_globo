package com.marioguerra.themovie.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marioguerra.themovie.R
import com.marioguerra.themovie.model.Movie
import com.marioguerra.themovie.util.IMAGE_URL
import com.marioguerra.themovie.util.MOVIE_KEY_INTENT
import com.marioguerra.themovie.view.activity.MovieActivity
import com.squareup.picasso.Picasso

class MoviesSearchAdapter (private val context: Context, private val movieList: ArrayList<Movie>)
    : RecyclerView.Adapter<ResultViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_movie, parent, false)
        return ResultViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {

        Picasso.with(context)
            .load(IMAGE_URL+movieList[position].poster_path)
            .into(holder.imageMovie)

        holder.tvTitle.text = movieList[position].title

        holder.imageMovie.setOnClickListener {
            val intent = Intent(context, MovieActivity::class.java)
            intent.putExtra(MOVIE_KEY_INTENT, movieList[position])
            context.startActivity(intent)
        }

    }
}