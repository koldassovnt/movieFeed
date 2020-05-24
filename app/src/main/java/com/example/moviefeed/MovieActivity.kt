package com.example.moviefeed

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.moviefeed.adapter.MovieAdapter
import com.example.moviefeed.pojo.Movie
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_movie.*

@Suppress("DEPRECATION")
class MovieActivity() : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var viewModel : MovieViewModel
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val adapter = MovieAdapter(this)
        rvMovieList.adapter = adapter

        handler = Handler()

        adapter.onMovieClickListener = object : MovieAdapter.OnMovieClickListener {
            override fun onMovieClick(movie: Movie) {
                val intent = MovieDetailActivity.newIntent(this@MovieActivity, movie.originalTitle.toString())
                startActivity(intent)
            }
        }

        viewModel = ViewModelProviders.of(this)[MovieViewModel::class.java]
        viewModel.movieList.observe(this, Observer {
            adapter.movieList = it
        })

        swipeRefreshLayout.setOnRefreshListener {
            // Initialize a new Runnable
            runnable = Runnable {
                viewModel.movieList.observe(this, Observer {
                    adapter.movieList = it
                })

                swipeRefreshLayout.isRefreshing = false
            }
            handler.postDelayed(
                runnable, 1500.toLong()
            )
        }


        swipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

    }

}
