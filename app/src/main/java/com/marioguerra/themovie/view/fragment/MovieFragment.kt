package com.marioguerra.themovie.view.fragment

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.marioguerra.themovie.R
import com.marioguerra.themovie.common.BaseFragment
import com.marioguerra.themovie.databinding.ActivityMainBinding
import com.marioguerra.themovie.model.Movie
import com.marioguerra.themovie.util.MOVIE_KEY_INTENT
import com.marioguerra.themovie.util.databinding.inflate
import com.marioguerra.themovie.view.activity.MainActivity
import com.marioguerra.themovie.view.activity.MovieActivity
import com.marioguerra.themovie.view.adapter.MovieAdapter
import com.marioguerra.themovie.viewmodel.MainActivityViewModel
import com.marioguerra.themovie.viewmodel.MainActivityViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MovieFragment : BaseFragment<MainActivity>(), MovieAdapter.ClickListener  {

    private val binding by inflate<MovieFragment, ActivityMainBinding>(R.layout.activity_main)

    private lateinit var rvMovie: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var movies: PagedList<Movie>

    @Inject
    lateinit var factory: MainActivityViewModelFactory


    private val viewModel: MainActivityViewModel by injectActivityVIewModels()


    private var rvAdapter = MovieAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this

        rvMovie = binding.rvMovies
        swipeRefreshLayout = binding.swipeLayout

        searchView.visibility = View.GONE

        initSwipeRefreshLayout()
        initRecyclerView()
        getPopularMovie()
    }


    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            getPopularMovie()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun getPopularMovie() {
//        viewModel.getAllMovies().observe(this, Observer {
//            it?.let {list: List<Movie> ->
//                rvAdapter.setMovie(list)
//            }
//        })

        viewModel.getMoviePagedList().observe(viewLifecycleOwner, Observer {
            it?.let { pagedList ->
                movies = pagedList
                rvAdapter.submitList(movies)
            }
        })
    }

    private fun initRecyclerView() {
        rvMovie.layoutManager =
            if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                GridLayoutManager(activity, 2)
            } else {
                GridLayoutManager(activity, 4)
            }

        rvMovie.adapter = rvAdapter
    }

    override fun onClickItemListener(movie: Movie) {
        Intent(activity, MovieActivity::class.java).also {
            it.putExtra(MOVIE_KEY_INTENT, movie)
            startActivity(it)
        }
    }



}
