package com.marioguerra.themovie.view

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.marioguerra.themovie.R
import com.marioguerra.themovie.view.fragment.MovieFragment
import com.marioguerra.themovie.view.fragment.SearchFragment


enum class MainTab(
    val position: Int,
    @IdRes val menuItemId: Int) {

    MOVIES(0, R.id.movies) {
        override fun createFragment() = MovieFragment()
    },
    SEARCH(1, R.id.search) {
        override fun createFragment() = SearchFragment()
    };

    abstract fun createFragment(): Fragment

    companion object {

        fun forPosition(position: Int) = values().firstOrNull { it.position == position } ?: error("Wrong position number")

        fun forMenuItemId(@IdRes menuItemId: Int) = values().firstOrNull { it.menuItemId == menuItemId } ?: error("Wrong menuItemId")
    }
}