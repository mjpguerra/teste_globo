package com.marioguerra.themovie.util.provider

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun io(): Scheduler
    fun ui(): Scheduler
    fun computation(): Scheduler
}