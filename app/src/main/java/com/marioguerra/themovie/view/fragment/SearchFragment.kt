package com.marioguerra.themovie.view.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.marioguerra.themovie.R
import com.marioguerra.themovie.common.BaseFragment
import com.marioguerra.themovie.databinding.FragmentSearchBinding
import com.marioguerra.themovie.model.Movie
import com.marioguerra.themovie.model.Results
import com.marioguerra.themovie.util.databinding.inflate
import com.marioguerra.themovie.util.extension.RxSearchObservable
import com.marioguerra.themovie.view.activity.MainActivity
import com.marioguerra.themovie.view.adapter.MoviesSearchAdapter
import com.marioguerra.themovie.viewmodel.MainActivityViewModel
import com.marioguerra.themovie.viewmodel.MainActivityViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.rvMovies
import kotlinx.android.synthetic.main.activity_main.searchView
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.HttpException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchFragment : BaseFragment<MainActivity>() {

    private val binding by inflate<SearchFragment, FragmentSearchBinding>(R.layout.fragment_search)
    @Inject
    lateinit var factory: MainActivityViewModelFactory
    private val viewModel: MainActivityViewModel by injectActivityVIewModels()
    private var disposable: Disposable? = null
    private var mCompositeDisposable: CompositeDisposable? = null
    private var moviesList: ArrayList<Movie>? = null
    private var moviesAdapter: MoviesSearchAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this

        mCompositeDisposable = CompositeDisposable()
        initRecyclerView()
        initSearchView()
        verifyItems()
    }

    private fun verifyItems() {
        Thread(Runnable {
            activity!!.runOnUiThread {
                if(this.moviesList == null || this.moviesList!!.size == 0){
                    this.txt_search.text = getString(R.string.message_search_movie)
                    this.txt_search.visibility = View.VISIBLE
                } else {
                    this.txt_search.visibility = View.GONE
                }
            }
        }).start()
    }

    private fun initSearchView() {
        mCompositeDisposable?.add(
            RxSearchObservable()
                .fromView(searchView)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(this::getMovies, this::handleError)
        )
    }

    private fun getMovies(movie: String) {
        Log.d("MOVIES", "Will perform API search: $movie")
        mCompositeDisposable?.add(
            viewModel
                .getMovies(movie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        )
    }

    private fun handleResponse(response: Results) {
        Log.d("MOVIES", "Receive response with ${response.movies.size} movies!")
        verifyItems()
        moviesList = ArrayList(response.movies)
        moviesAdapter = MoviesSearchAdapter(this!!.context!!, moviesList!!)
        rvMovies.adapter = moviesAdapter
    }

    private fun handleError(error: Throwable) {
        Log.e("MOVIES", "Receive error ${error.localizedMessage}")
        if(error is HttpException) {
            if(error.code() == 422) {
                moviesList!!.clear()
                rvMovies.recycledViewPool.clear()
                txt_search.text = getString(R.string.message_no_movie)
                txt_search.visibility = View.VISIBLE
            }
        }
    }

    private fun initRecyclerView() {
        rvMovies.layoutManager =
            if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                GridLayoutManager(activity, 2)
            } else {
                GridLayoutManager(activity, 4)
            }

        rvMovies.adapter = moviesAdapter
    }

    override fun onResume() {
        super.onResume()
        verifyItems()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }


}
