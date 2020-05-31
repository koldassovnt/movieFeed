package com.example.moviefeed.ui.scenes.movie

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.moviefeed.R
import com.example.moviefeed.data.api.ApiFactory
import com.example.moviefeed.data.api.ApiService
import com.example.moviefeed.data.api.POSTER_BASE_URL
import com.example.moviefeed.data.pojo.MovieDetails
import com.example.moviefeed.data.repository.NetworkState
import kotlinx.android.synthetic.main.activity_movie_detail.*
import java.text.NumberFormat
import java.util.*

@Suppress("DEPRECATION")
class MovieDetailActivity() : AppCompatActivity() {

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var movieRepository: MovieDetailRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieId: Int = intent.getIntExtra("id",1)

        val apiService : ApiService = ApiFactory.getClient()
        movieRepository = MovieDetailRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            setUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
            progress_bar.visibility = if (it == NetworkState.LOADED) View.GONE else View.VISIBLE
        })
    }

    fun setUI( it: MovieDetails){
        movie_title.text = it.title
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.voteAverage.toString()
        movie_overview.text = it.overview

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster);


    }

    private fun getViewModel(movieId:Int): MovieDetailViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailViewModel(movieRepository, movieId) as T
            }
        })[MovieDetailViewModel::class.java]
    }
}