package com.marioguerra.themovie.view.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.marioguerra.themovie.view.MainTab

class DashboardPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = MainTab.forPosition(position).createFragment()

    override fun getCount() = MainTab.values().size
}