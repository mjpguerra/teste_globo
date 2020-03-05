package com.marioguerra.themovie.view.adapter


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_movie.view.*

class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageMovie = itemView.imageMovie!!
    val tvTitle = itemView.tvTitle!!
}