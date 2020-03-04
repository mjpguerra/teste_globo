package com.marioguerra.themovie.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.marioguerra.themovie.R
import com.marioguerra.themovie.common.BaseActivity
import com.marioguerra.themovie.databinding.ActivityDashboardBinding
import com.marioguerra.themovie.util.databinding.contentView
import com.marioguerra.themovie.view.adapter.DashboardPagerAdapter
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : BaseActivity()  {

    private val binding by contentView<DashboardActivity, ActivityDashboardBinding>(R.layout.activity_dashboard)

    private val adapter = DashboardPagerAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this

        setupViewPager()
        setupBottomNavigationView()
    }

    private fun setupViewPager() {
        viewPager.also {
            it.adapter = adapter
            it.offscreenPageLimit = adapter.count - 1
            it.setSwipePagingEnabled(false)
        }
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            DashboardTab.forMenuItemId(it.itemId).let { tab ->
                binding.viewPager.setCurrentItem(tab.position, true)
                true
            }
        }
    }

    companion object {
        fun intent(context: Context) = Intent(context, DashboardActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_SINGLE_TOP }
    }
}