package com.example.moviefeed.ui.scenes.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviefeed.data.pojo.MovieDetails
import com.example.moviefeed.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailViewModel (private val movieRepository : MovieDetailRepository, movieId: Int)  : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  movieDetails : LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }



}