package com.marioguerra.themovie.view.activity

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.marioguerra.themovie.R
import com.marioguerra.themovie.common.Values
import com.marioguerra.themovie.databinding.ActivitySplashBinding
import com.marioguerra.themovie.util.databinding.contentView
import com.marioguerra.themovie.util.extension.startActivityWithFinish
import io.reactivex.Single
import kotlinx.android.synthetic.main.activity_intro.*
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    private val binding by contentView<SplashActivity, ActivitySplashBinding>(R.layout.activity_splash)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this

        startAnimation(patrickLeft, 350)
        startAnimation(patrickCenter, 540)
        startAnimation(patrickRight, 255)
        startAnimation(prepareLabel, 1000)
        startPopcornAnimation()
        startApp()
    }

    /**
     * Animate any view with infinite fade-in animation
     */
    private fun startAnimation(view: View, duration: Long) {
        val anim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        anim.duration = duration
        view.startAnimation(anim)
    }

    private fun startPopcornAnimation() {
        val anim = AnimationUtils.loadAnimation(this, R.anim.fade_in_zoom)
        popcornCircle.startAnimation(anim)
    }

    private fun startApp() = Single.timer(Values.SPLASH_TIME_IN_MILLIS, TimeUnit.MILLISECONDS)
        .map { openDashboard() }
        .subscribe()

    private fun openDashboard() = startActivityWithFinish(MainActivity.intent(this))
}
