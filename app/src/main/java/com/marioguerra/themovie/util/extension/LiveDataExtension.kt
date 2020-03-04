package com.marioguerra.themovie.util.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

inline fun <T> LiveData<T>.filter(crossinline predicate: (T) -> Boolean): LiveData<T> {
    val mediator = MediatorLiveData<T>()
    mediator.addSource(this) { t -> if (predicate(t)) mediator.postValue(t) }
    return mediator
}
