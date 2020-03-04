package com.marioguerra.themovie.common

import androidx.fragment.app.Fragment
import com.marioguerra.themovie.compose.ViewModelFragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<T : BaseActivity> : ViewModelFragment() {

    protected val compositeDisposable = CompositeDisposable()

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }


    @Suppress("UNCHECKED_CAST")
    fun parentActivity(): T = requireActivity() as T
}
