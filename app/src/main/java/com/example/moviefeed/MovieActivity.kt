package com.example.moviefeed

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.moviefeed.adapter.MovieAdapter
import com.example.moviefeed.api.ApiFactory
import com.example.moviefeed.pojo.Movie
import kotlinx.android.synthetic.main.activity_movie.*


@Suppress("DEPRECATION")
class MovieActivity() : AppCompatActivity(), View.OnTouchListener,
    ViewTreeObserver.OnScrollChangedListener {

    private lateinit var viewModel: MovieViewModel
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private lateinit var scrollView: ScrollView
    private var isTopOfPage = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val adapter = MovieAdapter(this)
        rvMovieList.adapter = adapter

        handler = Handler()
        scrollView = findViewById(R.id.scrollView)
        scrollView.setOnTouchListener(this)
        scrollView.viewTreeObserver.addOnScrollChangedListener(this)

        adapter.onMovieClickListener = object : MovieAdapter.OnMovieClickListener {
            override fun onMovieClick(movie: Movie) {
                val intent = MovieDetailActivity.newIntent(
                    this@MovieActivity,
                    movie.originalTitle.toString()
                )
                startActivity(intent)
            }
        }
        viewModel = ViewModelProviders.of(this)[MovieViewModel::class.java]
        viewModel.loadData()
        viewModel.movieList.observe(this, Observer {
            adapter.movieList = it
        })

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.clearData()
            ApiFactory.PAGE++
            viewModel.loadData()

            runnable = Runnable {
                viewModel.movieList.observe(this, Observer {
                    adapter.movieList = it
                })
                swipeRefreshLayout.isRefreshing = false
            }

            handler.postDelayed(
                runnable, 3000.toLong()
            )
        }

        swipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return false
    }

    override fun onScrollChanged() {
        val view = scrollView.getChildAt(scrollView.childCount - 1)
        val topDetector = scrollView.scrollY
        val bottomDetector =
            view.bottom - (scrollView.height + scrollView.scrollY)
        if (bottomDetector == 0) {

        }
        if (topDetector <= 0) {

        }
    }
}
