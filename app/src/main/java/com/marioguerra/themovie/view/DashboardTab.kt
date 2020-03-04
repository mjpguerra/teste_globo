package com.marioguerra.themovie.view

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.marioguerra.themovie.R


enum class DashboardTab(
    val position: Int,
    @IdRes val menuItemId: Int) {

    MOVIES(0, R.id.movies) {
        override fun createFragment() = MainActivity()
    },
    SEARCH(1, R.id.search) {
        override fun createFragment() = MainActivity()
    };

    abstract fun createFragment(): Fragment

    companion object {

        fun forPosition(position: Int) = values().firstOrNull { it.position == position } ?: error("Wrong position number")

        fun forMenuItemId(@IdRes menuItemId: Int) = values().firstOrNull { it.menuItemId == menuItemId } ?: error("Wrong menuItemId")
    }
}