package com.example.moviefeed.ui.scenes.movies

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviefeed.R
import com.example.moviefeed.data.api.ApiFactory
import com.example.moviefeed.data.api.ApiService
import com.example.moviefeed.data.repository.NetworkState
import kotlinx.android.synthetic.main.activity_movie.*

class MoviesActivity() : AppCompatActivity() {

    private lateinit var viewModel: MoviesViewModel
    lateinit var movieRepository: MoviesPagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val apiService: ApiService = ApiFactory.getClient()

        movieRepository = MoviesPagedListRepository(apiService)

        viewModel = getViewModel()

        val movieAdapter = MoviesAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 1)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                return if (viewType == movieAdapter.MOVIE_VIEW_TYPE)
                    1
                else
                    3
            }
        }

        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = movieAdapter

        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })
    }

    private fun getViewModel(): MoviesViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MoviesViewModel(movieRepository) as T
            }
        })[MoviesViewModel::class.java]
    }


}
